package com.kbulkup.training.service;

import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.request.TraineeTrainingDetailRequestDTO;
import com.kbulkup.training.dto.response.TraineeTrainingDetailResponseDTO;
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
     *  결제 후 트레이닝 상세 조회
     * - 루틴별 점수는 routines.score 사용
     * - totalScore는 sumRoutineScore()로 계산
     */
    public TraineeRoutineSummaryResponseDTO getTrainingDetail(Long trainingId, Long userId) {
        var training = traineeTrainingMapper.findTrainingById(trainingId, userId);
        if (training == null) {
            throw new BaseException(ResponseCode.TRAINING_NOT_FOUND);
        }

        var routines = traineeTrainingMapper.findRoutinesByTraining(trainingId, userId).stream()
                .map(r -> TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO.of(
                        r.getRoutineId(),
                        r.getTitle(),
                        r.isCompleted(),
                        r.getRewardPoint(),
                        r.getCompletedAt()
                )).toList();

        int completedCount = traineeTrainingMapper.countCompletedRoutines(trainingId, userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(trainingId);
        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;

        // 총점수 계산: sumRoutineScore()
        int totalRoutineScore = traineeTrainingMapper.sumRoutineScore(trainingId);

        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, (int) progress);

        return TraineeRoutineSummaryResponseDTO.of(
                training.getTitle(),
                training.getDescription(),
                training.getPrice(),
                training.getCategory(),
                training.getLevel(),
                totalRoutineScore,   //  루틴 점수 합산 반영
                training.getAverageRating(),
                training.getTraineeCount(),
                progress,
                routines
        );
    }

    /**
     *  결제 전 트레이닝 상세 조회
     * - totalRoutineScore는 findTrainingDetail 쿼리에서 SUM(score) 계산됨
     */
    public TraineeTrainingDetailResponseDTO getTrainingDetail(TraineeTrainingDetailRequestDTO request) {
        return traineeTrainingMapper.findTrainingDetail(request.getTrainingId());
    }

    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings() {
        return traineeTrainingMapper.findAllApprovedTrainings();
    }
}
