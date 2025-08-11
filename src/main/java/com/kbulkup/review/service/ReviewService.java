package com.kbulkup.review.service;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;

import java.util.List;

public interface ReviewService {
    List<TrainerTrainingReviewDetailResponseDTO> getTrainingReviews(Long trainingId);
}
