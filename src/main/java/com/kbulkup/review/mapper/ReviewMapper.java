package com.kbulkup.review.mapper;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<TrainerTrainingReviewDetailResponseDTO> findReviewsByTrainingId(Long trainingId);

    void updateTrainingReview(@Param("trainingId") Long trainingId);

    void updateTrainerReview(Long trainerId);
}
