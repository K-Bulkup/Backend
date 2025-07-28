package com.kbulkup.training.dto.response;

public class TrainingTrainerDetailProfileResponseDTO {

    private String name;
    private String profileUrl;
    private String description;
    private Integer traineeCount;
    private Float averageRating;
    private Boolean isCertified;

    public TrainingTrainerDetailProfileResponseDTO() {}

    public String getName() { return name; }
    public String getProfileUrl() { return profileUrl; }
    public String getDescription() { return description; }
    public Integer getTraineeCount() { return traineeCount; }
    public Float getAverageRating() { return averageRating; }
    public Boolean getIsCertified() { return isCertified; }
}
