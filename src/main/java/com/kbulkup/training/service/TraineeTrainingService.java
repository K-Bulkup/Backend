package com.kbulkup.training.service;

import com.kbulkup.training.dto.response.TrainingDetailResponseDTO;
import com.kbulkup.training.mapper.TraineeTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeTrainingService {

    private final TraineeTrainingMapper traineeTrainingMapper;

    @Transactional(readOnly = true)
    public TrainingDetailResponseDTO getTrainingDetail(Long trainingId, Long userId) {

        TrainingDetailResponseDTO training = traineeTrainingMapper.findTrainingById(trainingId, userId);
        if (training == null) {
            return null;
        }

        // 루틴 조회
        List<TrainingDetailResponseDTO.RoutineDTO> routines =
                traineeTrainingMapper.findRoutinesByTraining(trainingId, userId);
        training = TrainingDetailResponseDTO.builder()
                .title(training.getTitle())
                .description(training.getDescription())
                .price(training.getPrice())
                .category(training.getCategory())
                .level(training.getLevel())
                .totalScore(training.getTotalScore())
                .averageRating(training.getAverageRating())
                .traineeCount(training.getTraineeCount())
                .progress(training.getProgress())
                .completedAt(training.getCompletedAt())
                .routines(routines)
                .build();

        return training;
    }

    @Transactional
    public void updateTrainingProgress(Long trainingId, Long userId) {
        int completedCount = traineeTrainingMapper.countCompletedRoutines(trainingId, userId);
        int totalCount = traineeTrainingMapper.countTotalRoutines(trainingId);

        float progress = (totalCount == 0) ? 0 : ((float) completedCount / totalCount) * 100;
        traineeTrainingMapper.updateTrainingProgress(trainingId, userId, progress);
    }
}
