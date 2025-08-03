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
     * 트레이닝 상세 조회 (수강생 전용)
     */
    public TraineeRoutineSummaryResponseDTO getTrainingDetail(Long trainingId, Long userId) {
        // 1. 트레이닝 기본 정보 조회
        var training = traineeTrainingMapper.findTrainingById(trainingId, userId);

        // 존재하지 않는 트레이닝이거나 수강권한 없는 경우 예외 처리
        if (training == null) {
            throw new BaseException(ResponseCode.TRAINING_NOT_FOUND);
        }

        // 2. 루틴 목록 조회 (score는 DB 값 그대로 사용)
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

        // 4. 진행률 DB 업데이트
        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, (int) progress);

        // 5. DTO 반환
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

    /** 승인된 트레이닝 전체 목록 조회 */
    public List<TraineeTrainingListResponseDTO> getAllApprovedTrainings() {
        return traineeTrainingMapper.findAllApprovedTrainings();
    }

    /** 관리자용 트레이닝 상세 조회 */
    public TraineeTrainingDetailResponseDTO getTrainingDetail(TraineeTrainingDetailRequestDTO request) {
        return traineeTrainingMapper.findTrainingDetail(request.getTrainingId());
    }
}
