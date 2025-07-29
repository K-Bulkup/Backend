package com.kbulkup.training.mapper;

import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TraineeTrainerMapper {
    TrainingTrainerDetailProfileResponseDTO selectTrainerProfile(Long trainerId);
    List<TrainerTrainingSummaryResponseDTO> selectTrainerTrainings(Long trainerId);
}
