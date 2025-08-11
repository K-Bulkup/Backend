// --- RoutineResultServiceImpl.java ---
package com.kbulkup.routine.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kbulkup.routine.client.AiJudgeClient;
import com.kbulkup.routine.domain.RoutineResult;
import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import com.kbulkup.routine.mapper.RoutineResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineResultServiceImpl implements RoutineResultService {

    private final RoutineResultMapper routineResultMapper;
    private final AiJudgeClient aiJudgeClient;
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto, MultipartFile file) {
        String routineDescription = routineResultMapper.findRoutineDescriptionByRoutineId(routineId);

        String thumbnailUrl = null;

        if(file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String storedFileName = "routine_result/" + UUID.randomUUID() + "-" + originalFilename;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            try {
                amazonS3.putObject(bucket, storedFileName, file.getInputStream(), metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            thumbnailUrl = amazonS3.getUrl(bucket, storedFileName).toString();
        }

        boolean isCorrect = aiJudgeClient.evaluate(routineDescription, dto.getAnswerText(), thumbnailUrl);

        int score = routineResultMapper.selectRoutineScoreById(routineId);
        int awaredScore = isCorrect ? score : 0;

        boolean alreadySubmitted = routineResultMapper.existsByRoutineAndEnrollment(routineId, dto.getEnrollmentId()) > 0;

        RoutineResult result = RoutineResult.builder()
                .routineId(routineId)
                .enrollmentId(dto.getEnrollmentId())
                .answerText(dto.getAnswerText())
                .evidenceUrl(thumbnailUrl)
                .status(true)
                .awaredScore(awaredScore)
                .passFailResult(isCorrect ? PassFailResult.PASS : PassFailResult.FAIL)
                .submittedAt(LocalDateTime.now())
                .build();

        if (alreadySubmitted) {
            routineResultMapper.updateRoutineResult(result);
        } else {
            routineResultMapper.insertRoutineResult(result);
        }

        if (isCorrect) {
            routineResultMapper.increaseUserScore(dto.getEnrollmentId(), awaredScore);
            routineResultMapper.updateEnrollmentProgress(dto.getEnrollmentId());
        }

        return RoutineResultCreateResponseDTO.from(result.getPassFailResult());
    }
}
