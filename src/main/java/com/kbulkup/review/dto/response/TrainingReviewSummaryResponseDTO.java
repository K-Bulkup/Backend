package com.kbulkup.review.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@ApiModel(description = "트레이닝 리뷰 요약 응답")
public class TrainingReviewSummaryResponseDTO {

    @ApiModelProperty(value = "평균 평점")
    private double averageRating;

    @ApiModelProperty(value = "전체 리뷰 수")
    private int totalReviewCount;

    @ApiModelProperty(value = "리뷰 목록")
    private List<TrainerTrainingReviewDetailResponseDTO> reviews;
}
