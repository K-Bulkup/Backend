package com.kbulkup.training.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingSearchListResponseDTO {
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
