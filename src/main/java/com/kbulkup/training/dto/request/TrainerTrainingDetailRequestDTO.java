package com.kbulkup.training.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TrainerTrainingDetailRequestDTO {
    private Long trainingId;
    private Long trainerId;

    public static TrainerTrainingDetailRequestDTO of(Long trainingId, Long trainerId) {
        return new TrainerTrainingDetailRequestDTO(trainingId, trainerId);
    }
}