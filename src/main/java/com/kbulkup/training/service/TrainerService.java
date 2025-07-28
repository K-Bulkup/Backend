package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResonseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryDTO;
import java.util.List;

public interface TrainerService {
    TrainingTrainerDetailProfileResonseDTO getTrainerProfile(Long trainerId);
    List<TrainerTrainingSummaryDTO> getTrainerTrainings(Long trainerId);
}
