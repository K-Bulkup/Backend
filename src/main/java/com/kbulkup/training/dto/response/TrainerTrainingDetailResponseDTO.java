package com.kbulkup.training.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerTrainingDetailResponseDTO {
    private String difficulty;
    private String category;
    private int totalReward;

    private String trainerName;
    private String trainerProfileImage;
    private double trainingRating;
    private int enrolledTraineeCount;

    private String title;
    private String description;
    private String thumbnailUrl;

    @Builder
    public TrainerTrainingDetailResponseDTO(String difficulty, String category, int totalReward,
                                            String trainerName, String trainerProfileImage, double trainingRating,
                                            int enrolledTraineeCount, String title, String description,
                                            String thumbnailUrl) {
        this.difficulty = difficulty;
        this.category = category;
        this.totalReward = totalReward;
        this.trainerName = trainerName;
        this.trainerProfileImage = trainerProfileImage;
        this.trainingRating = trainingRating;
        this.enrolledTraineeCount = enrolledTraineeCount;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }
}
