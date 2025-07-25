package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;

public interface RoutineService {

    TraineeRoutineDetailResponseDTO getRoutineDetail(Long routineId);
}
