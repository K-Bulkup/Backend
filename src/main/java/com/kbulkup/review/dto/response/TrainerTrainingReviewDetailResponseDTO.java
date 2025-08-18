package com.kbulkup.review.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "트레이닝 리뷰 상세 항목")
public class TrainerTrainingReviewDetailResponseDTO {

    @ApiModelProperty(value = "작성자 닉네임")
    private String username;

    @ApiModelProperty(value = "평점")
    private int rating;

    @ApiModelProperty(value = "리뷰 내용")
    private String content;
}
