package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.response.TraineeRoutineDetailResponseDTO;

public interface RoutineService {

    TraineeRoutineDetailResponseDTO getRoutineDetail(Long routineId);
}
