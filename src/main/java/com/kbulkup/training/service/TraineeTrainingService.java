package com.kbulkup.training.service;

import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.routine.dto.RoutineSummaryResponseDTO;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeTrainingService {

    private final TraineeTrainingMapper traineeTrainingMapper;

    /** 결제 여부에 따라 다른 DTO 반환 */
    public Object getTrainingDetail(TraineeTrainingDetailRequestDTO request, Long userId) {
        boolean purchased = traineeTrainingMapper.isTrainingPurchased(request.getTrainingId(), userId);
        return purchased ? getPurchasedTrainingDetail(request, userId) : getUnpurchasedTrainingDetail(request);
    }

    /** 결제 후 상세 조회 (루틴 타입별 Map 사용) */
    private TraineeRoutineSummaryResponseDTO getPurchasedTrainingDetail(TraineeTrainingDetailRequestDTO request, Long userId) {
        var training = traineeTrainingMapper.findTrainingById(request.getTrainingId(), userId);
        if (training == null) throw new BaseException(ResponseCode.TRAINING_NOT_FOUND);

        var routines = traineeTrainingMapper.findRoutinesByTraining(request.getTrainingId(), userId);

        //  routineType 기준 그룹핑
        Map<String, List<TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO>> groupedRoutines =
                routines.stream().collect(Collectors.groupingBy(
                        RoutineSummaryResponseDTO::getRoutineType,
                        Collectors.mapping(r -> TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO.of(
                                r.getRoutineId(), r.getTitle(), r.isCompleted(), r.getRewardPoint(), r.getCompletedAt()
                        ), Collectors.toList())
                ));

        int completedCount = traineeTrainingMapper.countCompletedRoutines(request.getTrainingId(), userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(request.getTrainingId());
        float progress = (totalCount == 0) ? 0 : Math.round(((float) completedCount / totalCount) * 1000) / 10.0f;
        int totalRoutineScore = traineeTrainingMapper.sumRoutineScore(request.getTrainingId());

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
                training.getTrainerNickname(),
                training.getTrainerProfileUrl(),
                training.getTrainerId(),
                groupedRoutines
        );
    }

    /** 결제 전 상세 조회 */
    private TraineeTrainingDetailResponseDTO getUnpurchasedTrainingDetail(TraineeTrainingDetailRequestDTO request) {
        return traineeTrainingMapper.findTrainingDetail(request.getTrainingId());
    }

    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings(Long userId) {
        return traineeTrainingMapper.findAllApprovedTrainings(userId);
    }

    public TraineeTrainingReviewResponseDTO getTrainingTitle(Long trainingId) {
        return traineeTrainingMapper.findTrainingTitleByTrainingId(trainingId);
    }

    @Transactional
    public void createReview(Long userId, Long trainingId, TraineeTrainingReviewCreateDTO dto) {
        traineeTrainingMapper.insertReview(userId, trainingId, dto.getRating(), dto.getContent());
    }
}
