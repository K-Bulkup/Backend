package com.kbulkup.training.dto.response;

import lombok.Getter;

@Getter
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

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public void setTraineeCount(int traineeCount) {
        this.traineeCount = traineeCount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
