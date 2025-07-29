package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TrainerDetailResponseDTO;
import com.kbulkup.training.dto.response.TrainingTrainerDetailProfileResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingSummaryDTO;
import com.kbulkup.training.mapper.TraineeTrainerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TraineeTrainerMapper traineeTrainerMapper;

    @Override
    public TrainerDetailResponseDTO getTrainerDetail(Long trainerId) {
        TrainingTrainerDetailProfileResponseDTO profile = traineeTrainerMapper.selectTrainerProfile(trainerId);
        List<TrainerTrainingSummaryDTO> trainings = traineeTrainerMapper.selectTrainerTrainings(trainerId);
        return new TrainerDetailResponseDTO(profile, trainings);
    }
}
