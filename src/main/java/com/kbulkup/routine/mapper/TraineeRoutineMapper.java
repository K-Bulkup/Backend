package com.kbulkup.routine.mapper;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TraineeRoutineMapper {

    /**
     * 루틴 상세 정보 조회
     *
     * @param routineId 조회할 루틴 ID
     * @return TraineeRoutineDetailResponseDTO
     */
    TraineeRoutineDetailResponseDTO findRoutineDetailById(@Param("routineId") Long routineId);

}
