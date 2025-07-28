package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TrainingDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TraineeTrainingMapper {

    // 트레이닝 상세 정보 조회
    TrainingDetailResponseDTO findTrainingById(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    // 루틴 목록 조회 (루틴 완료 여부 포함)
    List<TrainingDetailResponseDTO.RoutineDTO> findRoutinesByTraining(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    // 완료된 루틴 수
    int countCompletedRoutines(@Param("trainingId") Long trainingId, @Param("userId") Long userId);

    // 전체 루틴 수
    int countTotalRoutines(@Param("trainingId") Long trainingId);

    // 진행률 업데이트
    void updateTrainingProgress(@Param("trainingId") Long trainingId, @Param("userId") Long userId, @Param("progress") float progress);
}
