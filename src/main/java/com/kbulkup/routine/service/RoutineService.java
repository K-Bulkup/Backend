package com.kbulkup.routine.service;

import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;

import java.util.List;

public interface RoutineService {
    void createRoutines(Long trainingId, List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines);
}
