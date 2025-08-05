package com.kbulkup.training.service;

import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
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
     * 트레이닝 상세 조회 (결제 여부 판별 후 적절한 DTO 반환)
     * - userId가 있고, 결제된 경우: TraineeRoutineSummaryResponseDTO 반환
     * - 결제되지 않은 경우: TraineeTrainingDetailResponseDTO 반환
     */
    public Object getTrainingDetail(TraineeTrainingDetailRequestDTO request, Long userId) {

        boolean purchased = traineeTrainingMapper.isTrainingPurchased(request.getTrainingId(), userId);

        if (purchased) {
            return getPurchasedTrainingDetail(request, userId);
        } else {
            return getUnpurchasedTrainingDetail(request);
        }
    }

    /**
     * [결제 후] 트레이닝 상세 조회 (팩토리 메서드 적용)
     */
    private TraineeRoutineSummaryResponseDTO getPurchasedTrainingDetail(TraineeTrainingDetailRequestDTO request, Long userId) {
        var training = traineeTrainingMapper.findTrainingById(request.getTrainingId(), userId);

        if (training == null) {
            throw new BaseException(ResponseCode.TRAINING_NOT_FOUND);
        }

        var routines = traineeTrainingMapper.findRoutinesByTraining(request.getTrainingId(), userId).stream()
                .map(r -> TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO.of(
                        r.getRoutineId(),
                        r.getTitle(),
                        r.isCompleted(),
                        r.getRewardPoint(),
                        r.getCompletedAt()
                )).toList();

        int completedCount = traineeTrainingMapper.countCompletedRoutines(request.getTrainingId(), userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(request.getTrainingId());
        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;

        int totalRoutineScore = traineeTrainingMapper.sumRoutineScore(request.getTrainingId());

        // 팩토리 메서드 사용
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
     * [결제 전] 트레이닝 상세 조회 (Mapper → DTO 그대로 반환)
     */
    private TraineeTrainingDetailResponseDTO getUnpurchasedTrainingDetail(TraineeTrainingDetailRequestDTO request) {
        return traineeTrainingMapper.findTrainingDetail(request.getTrainingId());
    }

    /**
     * 전체 트레이닝 목록 조회
     */
    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings(Long userId) {
        return traineeTrainingMapper.findAllApprovedTrainings(userId);
    }

    /**
     * 트레이닝 제목 조회
     */
    public TraineeTrainingReviewResponseDTO getTrainingTitle(Long trainingId) {
        return traineeTrainingMapper.findTrainingTitleByTrainingId(trainingId);
    }

    /**
     * 리뷰 작성
     */
    @Transactional
    public void createReview(Long userId, Long trainingId, TraineeTrainingReviewCreateDTO dto) {
        traineeTrainingMapper.insertReview(userId, trainingId, dto.getRating(), dto.getContent());
    }
}
