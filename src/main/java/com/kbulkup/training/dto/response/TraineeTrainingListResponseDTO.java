package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingListResponseDTO {
    private Long trainingId;
    private String title;
    private String trainerName;
    private float averageRating;
    private int traineeCount;
    private int price;
    private String thumbnailUrl;
    private String level;
    private String category;
}
