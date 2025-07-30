package com.kbulkup.training.service;

import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeTrainingService {

    private final TraineeTrainingMapper traineeTrainingMapper;

    /**
     * 트레이닝 상세 조회 (수강생 전용)
     */
    public TraineeRoutineSummaryResponseDTO getTrainingDetail(Long trainingId, Long userId) {
        // 1. 트레이닝 기본 정보 조회
        var training = traineeTrainingMapper.findTrainingById(trainingId, userId);

        // 2. 루틴 목록 조회 후 DTO 변환
        List<TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO> routines =
                traineeTrainingMapper.findRoutinesByTraining(trainingId, userId).stream()
                        .map(r -> TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO.of(
                                r.getRoutineId(),
                                r.getTitle(),
                                r.isCompleted(),
                                r.getRewardPoint(),
                                r.getCompletedAt()
                        ))
                        .toList();

        // 3. 진행률 계산
        int completedCount = traineeTrainingMapper.countCompletedRoutines(trainingId, userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(trainingId);
        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;

        // 4. DB에 진행률 갱신
        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, (int) progress);

        // 5. 팩토리 메서드를 이용해 DTO 생성 및 반환
        return TraineeRoutineSummaryResponseDTO.of(
                training.getTitle(),
                training.getDescription(),
                training.getPrice(),
                training.getCategory(),
                training.getLevel(),
                training.getTotalScore(),
                training.getAverageRating(),
                training.getTraineeCount(),
                progress,
                routines
        );
    }
    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings() {
        return traineeTrainingMapper.findAllApprovedTrainings();
    }

}
