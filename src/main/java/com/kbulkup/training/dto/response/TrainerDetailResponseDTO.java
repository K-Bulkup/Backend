package com.kbulkup.training.dto.response;

import java.util.List;

public class TrainerDetailResponseDTO {

    private TrainingTrainerDetailProfileResonseDTO trainer;
    private List<TrainerTrainingSummaryDTO> trainings;

    public TrainerDetailResponseDTO(TrainingTrainerDetailProfileResonseDTO trainer,
                                    List<TrainerTrainingSummaryDTO> trainings) {
        this.trainer = trainer;
        this.trainings = trainings;
    }

    public TrainingTrainerDetailProfileResonseDTO getTrainer() { return trainer; }
    public List<TrainerTrainingSummaryDTO> getTrainings() { return trainings; }
}
