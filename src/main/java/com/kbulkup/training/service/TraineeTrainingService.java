package com.kbulkup.training.service;

import com.kbulkup.routine.dto.RoutineSummaryResponseDTO;
import com.kbulkup.routine.dto.TraineeRoutineSummaryResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeTrainingService {

    private final TraineeTrainingMapper traineeTrainingMapper;

    public TraineeRoutineSummaryResponseDTO getTrainingDetail(Long trainingId, Long userId) {
        TraineeRoutineSummaryResponseDTO training = traineeTrainingMapper.findTrainingById(trainingId, userId);
        List<RoutineSummaryResponseDTO> routines = traineeTrainingMapper.findRoutinesByTraining(trainingId, userId);

        int completed = traineeTrainingMapper.countCompletedRoutines(trainingId, userId);
        int total = traineeTrainingMapper.countTotalRoutines(trainingId);
        int progress = total > 0 ? (completed * 100 / total) : 0;

        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, progress);

        return TraineeRoutineSummaryResponseDTO.builder()
                .title(training.getTitle())
                .description(training.getDescription())
                .price(training.getPrice())
                .category(training.getCategory())
                .level(training.getLevel())
                .totalScore(training.getTotalScore())
                .averageRating(training.getAverageRating())
                .traineeCount(training.getTraineeCount())
                .progress(progress)
                .routines(routines)
                .build();
    }
}
