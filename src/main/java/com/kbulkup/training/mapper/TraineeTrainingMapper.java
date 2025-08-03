package com.kbulkup.training.mapper;

import com.kbulkup.routine.dto.RoutineSummaryResponseDTO;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingDetailResponseDTO;
import com.kbulkup.training.dto.response.TraineeTrainingListResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TraineeTrainingMapper {

    /** 트레이닝 기본 정보 조회 */
    TraineeRoutineSummaryResponseDTO findTrainingById(@Param("trainingId") Long trainingId,
                                                      @Param("userId") Long userId);

    /** 루틴 목록 조회 (routines.score 사용) */
    List<RoutineSummaryResponseDTO> findRoutinesByTraining(@Param("trainingId") Long trainingId,
                                                           @Param("userId") Long userId);

    /** 완료된 루틴 수 */
    int countCompletedRoutines(@Param("trainingId") Long trainingId,
                               @Param("userId") Long userId);

    /** 전체 루틴 수 */
    int countTotalRoutines(@Param("trainingId") Long trainingId);

    /** 진행률 갱신 */
    void updateTrainingProgress(@Param("trainingId") Long trainingId,
                                @Param("userId") Long userId,
                                @Param("progress") int progress);

    /** 승인된 트레이닝 목록 조회 */
    List<TraineeTrainingListResponseDTO> findAllApprovedTrainings();

    /** 트레이닝 상세 정보 */
    TraineeTrainingDetailResponseDTO findTrainingDetail(@Param("trainingId") Long trainingId);
}
