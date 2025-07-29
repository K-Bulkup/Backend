package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTrainerDetailProfileResponseDTO {

    private String name;
    private String profileUrl;
    private String description;
    private Integer traineeCount;
    private Float averageRating;
    private Boolean isCertified;
}
