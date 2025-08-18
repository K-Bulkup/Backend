package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이너 트레이닝 요약")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingSummaryResponseDTO {
    @ApiModelProperty("트레이닝 ID") private Long trainingId;
    @ApiModelProperty("제목") private String title;
    @ApiModelProperty("난이도") private String level;
    @ApiModelProperty("썸네일 URL") private String thumbnailUrl;
    @ApiModelProperty("가격") private Long price;
    @ApiModelProperty("평균 평점") private Double averageRating;
}
