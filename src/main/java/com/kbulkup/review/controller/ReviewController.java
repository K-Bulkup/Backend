package com.kbulkup.review.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.review.dto.response.TrainingReviewSummaryResponseDTO;
import com.kbulkup.review.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "Review", description = "트레이닝 리뷰 조회 API")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "트레이닝 리뷰 요약 조회", notes = "평균 평점, 리뷰 개수, 리뷰 목록을 반환합니다.")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/common/reviews/{trainingId}")
    public CustomResponse<TrainingReviewSummaryResponseDTO> getTrainerTrainingReview(@PathVariable Long trainingId) {
        TrainingReviewSummaryResponseDTO dto = reviewService.getTrainingReviews(trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, dto);
    }
}
