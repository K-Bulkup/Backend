// --- RoutineResultServiceImpl.java ---
package com.kbulkup.routine.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kbulkup.common.exception.QuizException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.gpt.service.GPTService;
import com.kbulkup.routine.client.AiJudgeClient;
import com.kbulkup.routine.domain.RoutineResult;
import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import com.kbulkup.routine.dto.response.UserAnswerDTO;
import com.kbulkup.routine.mapper.RoutineResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineResultServiceImpl implements RoutineResultService {

    private final RoutineResultMapper routineResultMapper;
    private final AiJudgeClient aiJudgeClient;
    private final AmazonS3 amazonS3;
    private final GPTService gptService;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto, MultipartFile file) {
        final String quizType = Optional.ofNullable(
                routineResultMapper.findQuizTypeByRoutineId(routineId)
        ).orElseThrow(() -> new QuizException(ResponseCode.QUIZ_TYPE_NOT_FOUND));

        final String routineDescription =
                routineResultMapper.findRoutineDescriptionByRoutineId(routineId);

        final String thumbnailUrl = (file != null && !file.isEmpty())
                ? uploadToS3(file) : null;

        final boolean isCorrect = evaluateByType(
                quizType, routineId, dto.getAnswerText(), routineDescription, thumbnailUrl
        ); //

        final int score = routineResultMapper.selectRoutineScoreById(routineId);
        final int awardedScore = isCorrect ? score : 0;

        final boolean alreadySubmitted =
                routineResultMapper.existsByRoutineAndEnrollment(routineId, dto.getEnrollmentId()) > 0;

        RoutineResult result = RoutineResult.builder()
                .routineId(routineId)
                .enrollmentId(dto.getEnrollmentId())
                .answerText(dto.getAnswerText())
                .evidenceUrl(thumbnailUrl)
                .status(true)
                .awaredScore(awardedScore)
                .passFailResult(isCorrect ? PassFailResult.PASS : PassFailResult.FAIL)
                .submittedAt(LocalDateTime.now())
                .build();

        if (alreadySubmitted) {
            routineResultMapper.updateRoutineResult(result);
        } else {
            routineResultMapper.insertRoutineResult(result);
        }

        if (isCorrect) {
            routineResultMapper.increaseUserScore(dto.getEnrollmentId(), awardedScore);
            routineResultMapper.updateEnrollmentProgress(dto.getEnrollmentId());
        }

        String preCommentary = (String) gptService.requestOnlyText(routineDescription, dto.getAnswerText()).getChoices().get(0).getMessage().getContent();
        String comment = preCommentary.split("\\R", 2)[0];
        return RoutineResultCreateResponseDTO.from(result.getPassFailResult(), comment);
    }

    @Override
    public UserAnswerDTO getUserAnswer(Long routineId, Long trainingId, Long userId) {
        Long enrollmentId = routineResultMapper.findEnrollmentId(userId, trainingId);
        return routineResultMapper.findAnswer(enrollmentId, routineId);
    }

    private String uploadToS3(MultipartFile file) {
        final String thumbnailUrl;
        String originalFilename = file.getOriginalFilename();
        String storedFileName = "routine-result/" + UUID.randomUUID() + "-" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucket, storedFileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new QuizException(ResponseCode.S3_UPLOAD_FAILED);
        }

        thumbnailUrl = amazonS3.getUrl(bucket, storedFileName).toString();
        return thumbnailUrl;
    }

    private boolean evaluateByType(String quizType,
                                   Long routineId,
                                   String userAnswer,
                                   String routineDescription,
                                   String evidenceUrl) {

        switch (quizType.toUpperCase()) {
            case "OX": {
                String correct = routineResultMapper.findRoutineAnswerByRoutineId(routineId)
                        .orElseThrow(() ->  new QuizException(ResponseCode.QUIZ_TYPE_NOT_FOUND));
                return normalize(correct).equals(normalize(userAnswer));
            }
            case "PHOTO": {
                return aiJudgeClient.evaluate(routineDescription, userAnswer, evidenceUrl);
            }
            case "SHORT_ANSWER": {
                return aiJudgeClient.evaluate(routineDescription, userAnswer, null);
            }
            default:
                throw new QuizException(ResponseCode.QUIZ_TYPE_NOT_FOUND);
        }
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\s+", " ").toUpperCase();
    }
}

