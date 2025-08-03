package com.kbulkup.training.service;

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

    /** 난이도별 고정 리워드 값 */
    private static final int REWARD_BEGINNER = 2;
    private static final int REWARD_INTERMEDIATE = 3;
    private static final int REWARD_ADVANCED = 5;

    /**
     * 트레이닝 상세 조회 (수강생 전용)
     */
    public TraineeRoutineSummaryResponseDTO getTrainingDetail(Long trainingId, Long userId) {
        // 1. 트레이닝 기본 정보 조회
        var training = traineeTrainingMapper.findTrainingById(trainingId, userId);

        // 2. 루틴 목록 조회 → 난이도별 리워드 직접 세팅
        List<TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO> routines =
                traineeTrainingMapper.findRoutinesByTraining(trainingId, userId).stream()
                        .map(r -> TraineeRoutineSummaryResponseDTO.RoutineSummaryResponseDTO.of(
                                r.getRoutineId(),
                                r.getTitle(),
                                r.isCompleted(),
                                resolveRewardByLevel(training.getLevel()), // 난이도별 고정 리워드 적용
                                r.getCompletedAt()
                        ))
                        .toList();

        // 3. 진행률 계산 (완료 루틴 / 전체 루틴)
        int completedCount = traineeTrainingMapper.countCompletedRoutines(trainingId, userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(trainingId);
        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;

        // 4. DB에 진행률 갱신
        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, (int) progress);

        // 5. 팩토리 메서드로 DTO 생성 후 반환
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

    /** 난이도 → 고정 리워드 매핑 */
    private int resolveRewardByLevel(String level) {
        return switch (level) {
            case "초급" -> REWARD_BEGINNER;
            case "중급" -> REWARD_INTERMEDIATE;
            case "고급" -> REWARD_ADVANCED;
            default -> 0;
        };
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
