package com.kbulkup.training.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 리뷰 작성 요청")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingReviewCreateDTO {
    @ApiModelProperty(value = "평점", required = true)
    private int rating;

    @ApiModelProperty(value = "리뷰 내용", required = true)
    private String content;
}
