package com.kbulkup.review.service;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import com.kbulkup.review.dto.response.TrainingReviewSummaryResponseDTO;

import java.util.List;

public interface ReviewService {
    TrainingReviewSummaryResponseDTO getTrainingReviews(Long trainingId);
}
