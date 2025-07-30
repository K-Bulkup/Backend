package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeTrainingDetailResponseDTO {

    private String title;
    private String description;
    private int price;
    private String category;
    private String level;
    private float averageRating;
    private int traineeCount;
    private String trainerNickname;
    private String trainerProfileUrl;
    private String thumbnailUrl;

    public static TraineeTrainingDetailResponseDTO of(String title, String description, int price,
                                                      String category, String level, float averageRating,
                                                      int traineeCount, String trainerNickname,
                                                      String trainerProfileUrl, String thumbnailUrl) {
        return TraineeTrainingDetailResponseDTO.builder()
                .title(title)
                .description(description)
                .price(price)
                .category(category)
                .level(level)
                .averageRating(averageRating)
                .traineeCount(traineeCount)
                .trainerNickname(trainerNickname)
                .trainerProfileUrl(trainerProfileUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
