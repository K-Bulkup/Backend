package com.kbulkup.review.mapper;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<TrainerTrainingReviewDetailResponseDTO> findReviewsByTrainingId(Long trainingId);
}
