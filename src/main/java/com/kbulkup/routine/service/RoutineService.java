package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;

import java.util.List;

public interface RoutineService {

    TraineeRoutineDetailResponseDTO getRoutineDetail(Long routineId);
}
