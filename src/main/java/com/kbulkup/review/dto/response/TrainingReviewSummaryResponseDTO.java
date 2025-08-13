package com.kbulkup.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TrainingReviewSummaryResponseDTO {
    private double averageRating;
    private int totalReviewCount;
    private List<TrainerTrainingReviewDetailResponseDTO> reviews;
}
