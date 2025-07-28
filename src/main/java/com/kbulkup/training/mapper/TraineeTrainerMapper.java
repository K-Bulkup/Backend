package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResonseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TraineeTrainerMapper {
    TrainingTrainerDetailProfileResonseDTO selectTrainerProfile(Long trainerId);
    List<TrainerTrainingSummaryDTO> selectTrainerTrainings(Long trainerId);
}
