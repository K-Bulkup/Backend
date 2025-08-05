package com.kbulkup.review.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import com.kbulkup.review.service.ReviewService;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/trainer/reviews/{trainingId}")
    public CustomResponse<List<TrainerTrainingReviewDetailResponseDTO>> getTrainerTrainingReview(@PathVariable Long trainingId) {
        List<TrainerTrainingReviewDetailResponseDTO> dtos = reviewService.getTrainingReviews(trainingId);
        return CustomResponse.success(ResponseCode.SUCCESS, dtos);
    }
}
