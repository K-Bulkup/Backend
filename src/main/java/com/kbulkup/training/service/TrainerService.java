package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryDTO;
import java.util.List;

public interface TrainerService {
    TrainingTrainerDetailProfileResponseDTO getTrainerProfile(Long trainerId);
    List<TrainerTrainingSummaryDTO> getTrainerTrainings(Long trainerId);
}
