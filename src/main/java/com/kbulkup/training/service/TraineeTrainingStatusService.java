package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TraineeTrainingStatusResponseDTO;

public interface TraineeTrainingStatusService {
    TraineeTrainingStatusResponseDTO getTrainingStatus(Long userId, Long trainingId);
}
