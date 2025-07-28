package com.kbulkup.training.dto.response;

public class TrainerTrainingSummaryDTO {

    private Long trainingId;
    private String title;
    private String level;
    private String thumbnailUrl;

    public TrainerTrainingSummaryDTO() {}

    public Long getTrainingId() { return trainingId; }
    public String getTitle() { return title; }
    public String getLevel() { return level; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}
