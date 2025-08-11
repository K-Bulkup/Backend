package com.kbulkup.training.service;

import com.kbulkup.counseling.mapper.CounselingMapper;
import com.kbulkup.review.mapper.ReviewMapper;
import com.kbulkup.training.dto.response.TraineeTrainingStatusResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeTrainingStatusServiceImpl implements TraineeTrainingStatusService {

    private final ReviewMapper reviewMapper;
    private final CounselingMapper counselingMapper;

    @Override
    public TraineeTrainingStatusResponseDTO getTrainingStatus(Long userId, Long trainingId) {
        Boolean hasWrittenReview = reviewMapper.hasWrittenReview(userId, trainingId);
        Boolean existsByUserIdAndTrainingId = counselingMapper.existsByUserIdAndTrainingId(userId, trainingId);

        return TraineeTrainingStatusResponseDTO.create(hasWrittenReview, existsByUserIdAndTrainingId);
    }
}
