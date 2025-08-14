package com.kbulkup.review.service;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import com.kbulkup.review.dto.response.TrainingReviewSummaryResponseDTO;
import com.kbulkup.review.mapper.ReviewMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final TrainingMapper trainingMapper;

    @Override
    public TrainingReviewSummaryResponseDTO getTrainingReviews(Long trainingId) {

        List<TrainerTrainingReviewDetailResponseDTO> reviewList = reviewMapper.findReviewsByTrainingId(trainingId);

        // 계산된 평균 평점을 가져온다.
        Double avgRating = trainingMapper.findAverageRatingByTrainingId(trainingId);

        return TrainingReviewSummaryResponseDTO.builder()
                .averageRating(avgRating != null ? avgRating : 0.0)
                .totalReviewCount(reviewList.size())
                .reviews(reviewList)
                .build();
    }
}
