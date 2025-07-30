package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TraineeEnrollmentResponseDTO;
import com.kbulkup.training.mapper.TraineeEnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * [수강생] 수강 목록 서비스
 * - 진행률 계산 및 DB 업데이트 포함
 * - Mapper에서 DTO 매핑 후 Service에서 최신 진행률 갱신
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TraineeEnrollmentService {

    private final TraineeEnrollmentMapper traineeEnrollmentMapper;

    /**
     * 수강 목록 조회
     * - DB에서 DTO 조회
     * - 진행률 재계산 후 DB 업데이트
     * - 최신 진행률로 DTO 재생성하여 반환
     */
    public List<TraineeEnrollmentResponseDTO> getEnrollments(Long userId) {
        List<TraineeEnrollmentResponseDTO> enrollments = traineeEnrollmentMapper.findTraineeEnrollments(userId);
        List<TraineeEnrollmentResponseDTO> responses = new ArrayList<>();

        for (TraineeEnrollmentResponseDTO dto : enrollments) {
            float updatedProgress = calculateAndUpdateProgress(dto.getTrainingId(), userId);

            responses.add(
                    TraineeEnrollmentResponseDTO.of(
                            dto.getTrainingId(),
                            dto.getTitle(),
                            dto.getThumbnailUrl(),
                            updatedProgress,   // ✅ 최신 진행률 적용
                            dto.getCreatedAt()
                    )
            );
        }
        return responses;
    }

    /**
     *  진행률 계산 및 DB 업데이트
     * @param trainingId 트레이닝 ID
     * @param userId     수강생 ID
     * @return 계산된 진행률(%)
     */
    private float calculateAndUpdateProgress(Long trainingId, Long userId) {
        int totalCount = traineeEnrollmentMapper.countTotalRoutines(trainingId);
        int completedCount = traineeEnrollmentMapper.countCompletedRoutines(trainingId, userId);
        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;

        traineeEnrollmentMapper.updateTrainingProgress(trainingId, userId, progress);
        return progress;
    }
}
