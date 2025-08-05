package com.kbulkup.training.mapper;

import com.kbulkup.routine.dto.RoutineSummaryResponseDTO;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingDetailResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingReviewResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TraineeTrainingMapper {

    /**  결제 후 트레이닝 기본 정보 조회 */
    TraineeRoutineSummaryResponseDTO findTrainingById(@Param("trainingId") Long trainingId,
                                                      @Param("userId") Long userId);

    /** 루틴 목록 조회 */
    List<RoutineSummaryResponseDTO> findRoutinesByTraining(@Param("trainingId") Long trainingId,
                                                           @Param("userId") Long userId);

    int countCompletedRoutines(@Param("trainingId") Long trainingId,
                               @Param("userId") Long userId);

    int countTotalRoutines(@Param("trainingId") Long trainingId);

    void updateTrainingProgress(@Param("trainingId") Long trainingId,
                                @Param("userId") Long userId,
                                @Param("progress") int progress);

    boolean isTrainingPurchased(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    List<TraineeTrainingListResponseDTO> findAllApprovedTrainings(@Param("userId") Long userId);

    /**  결제 전 트레이닝 상세 조회 (루틴 총점수 포함) */
    TraineeTrainingDetailResponseDTO findTrainingDetail(@Param("trainingId") Long trainingId);

    /**  루틴 총점수 합산 (결제 후 totalScore 계산용) */
    int sumRoutineScore(@Param("trainingId") Long trainingId);

    TraineeTrainingReviewResponseDTO findTrainingTitleByTrainingId(@Param("trainingId") Long trainingId);

    void insertReview(@Param("userId") Long userId, @Param("trainingId") Long trainingId, @Param("rating") int rating, @Param("content") String content);
}
