package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;

public interface TraineeRoutineQueryService {

    /**
     * 루틴 상세 정보를 조회합니다.
     * @param routineId 루틴 ID
     * @return 루틴 상세 정보 DTO
     */
    TraineeRoutineDetailResponseDTO getRoutineDetail(Long routineId);

}
