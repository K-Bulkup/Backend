package com.kbulkup.training.service;

import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;

public interface TrainingService {
    void createTraining(Long trainerId, TrainerTrainingCreateRequestDTO dto);
}
