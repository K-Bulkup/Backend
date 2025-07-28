package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDetailResponseDTO {

    private TrainingTrainerDetailProfileResponseDTO trainer;
    private List<TrainerTrainingSummaryDTO> trainings;
}
