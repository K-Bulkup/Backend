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

    TraineeRoutineSummaryResponseDTO findTrainingById(@Param("trainingId") Long trainingId,
                                                      @Param("userId") Long userId);

    List<RoutineSummaryResponseDTO> findRoutinesByTraining(@Param("trainingId") Long trainingId,
                                                           @Param("userId") Long userId);

    int countCompletedRoutines(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    int countTotalRoutines(@Param("trainingId") Long trainingId);

    void updateTrainingProgress(@Param("trainingId") Long trainingId,
                                @Param("userId") Long userId,
                                @Param("progress") int progress);

    /** 승인된 트레이닝 전체 목록 조회 */
    List<TraineeTrainingListResponseDTO> findAllApprovedTrainings();

    TraineeTrainingDetailResponseDTO findTrainingDetail(@Param("trainingId") Long trainingId);

    TraineeTrainingReviewResponseDTO findTrainingTitleByTrainingId(@Param("trainingId") Long trainingId);

    void insertReview(@Param("userId") Long userId, @Param("trainingId") Long trainingId, @Param("rating") int rating, @Param("content") String content);
}
