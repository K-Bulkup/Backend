package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerDetailResponseDTO {

    private TrainingTrainerDetailProfileResponseDTO trainer;
    private List<TrainerTrainingSummaryResponseDTO> trainings;

    public static TrainerDetailResponseDTO create(TrainingTrainerDetailProfileResponseDTO trainer,
                                                  List<TrainerTrainingSummaryResponseDTO> trainings) {
        return TrainerDetailResponseDTO.builder()
                .trainer(trainer)
                .trainings(trainings)
                .build();
    }
}

