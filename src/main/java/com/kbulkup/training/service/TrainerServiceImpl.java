package com.kbulkup.training.service;

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
    public TrainingTrainerDetailProfileResponseDTO getTrainerProfile(Long trainerId) {
        return traineeTrainerMapper.selectTrainerProfile(trainerId);
    }

    @Override
    public List<TrainerTrainingSummaryDTO> getTrainerTrainings(Long trainerId) {
        return traineeTrainerMapper.selectTrainerTrainings(trainerId);
    }
}
