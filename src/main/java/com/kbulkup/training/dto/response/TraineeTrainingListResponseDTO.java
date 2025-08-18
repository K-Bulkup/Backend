package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "수강생용 트레이닝 목록 항목")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingListResponseDTO {
    @ApiModelProperty("트레이닝 ID") private Long trainingId;
    @ApiModelProperty("제목") private String title;
    @ApiModelProperty("트레이너 이름") private String trainerName;
    @ApiModelProperty("평균 평점") private float averageRating;
    @ApiModelProperty("수강생 수") private int traineeCount;
    @ApiModelProperty("가격") private int price;
    @ApiModelProperty("썸네일 URL") private String thumbnailUrl;
    @ApiModelProperty("난이도") private String level;
    @ApiModelProperty("카테고리") private String category;
    @ApiModelProperty("구매 여부") private boolean purchased;
}
