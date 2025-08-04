package com.kbulkup.review.service;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import com.kbulkup.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    @Override
    public List<TrainerTrainingReviewDetailResponseDTO> getTrainingReviews(Long trainingId) {
        return reviewMapper.findReviewsByTrainingId(trainingId);
    }
}
