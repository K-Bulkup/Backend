package com.kbulkup.training.mapper;

import com.kbulkup.qna.dto.response.TrainerTrainingListDetailResponseDTO;
import com.kbulkup.training.domain.Training;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainingMapper {
    void createTraining(Training training);

    Long findTrainerByTrainingId(@Param("trainingId") Long trainingId);

    List<TrainerTrainingListDetailResponseDTO> findTrainerTrainings(@Param("trainerId") Long trainerId);

    Double findAverageRatingByTrainingId(@Param("trainingId") Long trainingId);
}
