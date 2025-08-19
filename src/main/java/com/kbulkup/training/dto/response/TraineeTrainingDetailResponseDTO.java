package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 상세(수강생 화면)")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeTrainingDetailResponseDTO {
    @ApiModelProperty("제목") private String title;
    @ApiModelProperty("설명") private String description;
    @ApiModelProperty("가격") private int price;
    @ApiModelProperty("카테고리") private String category;
    @ApiModelProperty("난이도") private String level;
    @ApiModelProperty("평균 평점") private float averageRating;
    @ApiModelProperty("수강생 수") private int traineeCount;
    @ApiModelProperty("트레이너 닉네임") private String trainerNickname;
    @ApiModelProperty("트레이너 프로필 URL") private String trainerProfileUrl;
    @ApiModelProperty("썸네일 URL") private String thumbnailUrl;
    @ApiModelProperty("루틴 총점수") private int totalRoutineScore;
    @ApiModelProperty("트레이너 ID") private Long trainerId;

    public static TraineeTrainingDetailResponseDTO of(String title, String description, int price,
                                                      String category, String level, float averageRating,
                                                      int traineeCount, String trainerNickname,
                                                      String trainerProfileUrl, String thumbnailUrl,
                                                      int totalRoutineScore, Long trainerId) {
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
                .totalRoutineScore(totalRoutineScore)
                .trainerId(trainerId)
                .build();
    }
}
