package com.kbulkup.training.dto.response;

import java.util.List;

public class TrainerDetailResponseDTO {

    private TrainingTrainerDetailProfileResponseDTO trainer;
    private List<TrainerTrainingSummaryDTO> trainings;

    public TrainerDetailResponseDTO(TrainingTrainerDetailProfileResponseDTO trainer,
                                    List<TrainerTrainingSummaryDTO> trainings) {
        this.trainer = trainer;
        this.trainings = trainings;
    }

    public TrainingTrainerDetailProfileResponseDTO getTrainer() { return trainer; }
    public List<TrainerTrainingSummaryDTO> getTrainings() { return trainings; }
}
