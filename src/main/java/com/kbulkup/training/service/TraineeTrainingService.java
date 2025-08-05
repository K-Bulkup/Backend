package com.kbulkup.training.service;

import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.request.TraineeTrainingDetailRequestDTO;
import com.kbulkup.training.dto.request.TraineeTrainingReviewCreateDTO;
import com.kbulkup.training.dto.response.TraineeTrainingDetailResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingReviewResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeTrainingService {

    private final TraineeTrainingMapper traineeTrainingMapper;

    /**
     *  결제 후 트레이닝 상세 조회
     * - Mapper에서 받은 데이터를 DTO로 변환 후 반환
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

        //  총점수 DB에서 합산
        int totalRoutineScore = traineeTrainingMapper.sumRoutineScore(trainingId);

        return TraineeRoutineSummaryResponseDTO.of(
                training.getTitle(),
                training.getDescription(),
                training.getPrice(),
                training.getCategory(),
                training.getLevel(),
                totalRoutineScore,
                training.getAverageRating(),
                training.getTraineeCount(),
                progress,
                routines
        );
    }

    /**
     *  결제 전 트레이닝 상세 조회
     * - Mapper 반환을 그대로 DTO로 사용
     */
    public TraineeTrainingDetailResponseDTO getTrainingDetail(TraineeTrainingDetailRequestDTO request) {
        return traineeTrainingMapper.findTrainingDetail(request.getTrainingId());
    }

    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings() {
        return traineeTrainingMapper.findAllApprovedTrainings();
    }
    public TraineeTrainingReviewResponseDTO getTrainingTitle (Long trainingId) {
        return traineeTrainingMapper.findTrainingTitleByTrainingId(trainingId);
    }

    @Transactional
    public void createReview(Long userId, Long trainingId, TraineeTrainingReviewCreateDTO dto) {
        traineeTrainingMapper.insertReview(userId, trainingId, dto.getRating(), dto.getContent());
    }
}
