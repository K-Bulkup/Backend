// --- RoutineResultServiceImpl.java ---
package com.kbulkup.routine.service;

import com.kbulkup.routine.client.AiJudgeClient;
import com.kbulkup.routine.domain.RoutineResult;
import com.kbulkup.routine.dto.request.RoutineResultCreateRequestDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO;
import com.kbulkup.routine.dto.response.RoutineResultCreateResponseDTO.PassFailResult;
import com.kbulkup.routine.mapper.RoutineResultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoutineResultServiceImpl implements RoutineResultService {

    private final RoutineResultMapper routineResultMapper;
    private final AiJudgeClient aiJudgeClient;

    @Override
    @Transactional
    public RoutineResultCreateResponseDTO submitResult(Long routineId, RoutineResultCreateRequestDTO dto) {
        String routineDescription = routineResultMapper.findRoutineDescriptionByRoutineId(routineId); // 루틴 질문 ( 추후 주석 삭제 )

        // evidence_url은 S3 트레이너 실천형 수행 결과 이미지 주소 ( 추후 주석 삭제 )
        boolean isCorrect = aiJudgeClient.evaluate(routineDescription, dto.getAnswerText(), dto.getEvidenceUrl());

        int score = routineResultMapper.selectRoutineScoreById(routineId);
        int awaredScore = isCorrect ? score : 0;

        boolean alreadySubmitted = routineResultMapper.existsByRoutineAndEnrollment(routineId, dto.getEnrollmentId()) > 0;

        RoutineResult result = RoutineResult.builder()
                .routineId(routineId)
                .enrollmentId(dto.getEnrollmentId())
                .answerText(dto.getAnswerText())
                .evidenceUrl(dto.getEvidenceUrl())
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
