package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 상세(공용 화면)")
@Getter
@NoArgsConstructor
public class TrainerTrainingDetailResponseDTO {
    @ApiModelProperty("난이도") private String difficulty;
    @ApiModelProperty("카테고리") private String category;
    @ApiModelProperty("총 리워드") private int totalReward;

    @ApiModelProperty("트레이너 이름") private String trainerName;
    @ApiModelProperty("트레이너 프로필 이미지") private String trainerProfileImage;
    @ApiModelProperty("평점") private double trainingRating;
    @ApiModelProperty("수강생 수") private int enrolledTraineeCount;

    @ApiModelProperty("제목") private String title;
    @ApiModelProperty("설명") private String description;
    @ApiModelProperty("썸네일 URL") private String thumbnailUrl;

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
