package com.kbulkup.training.service;

import com.kbulkup.routine.domain.Routine;
import com.kbulkup.training.mapper.TrainingRoutineMapper;
import com.kbulkup.training.domain.Training;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingMapper trainingMapper;
    private final TrainingRoutineMapper trainingRoutineMapper;

    @Override
    @Transactional
    public void createTraining(Long trainerId, TrainerTrainingCreateRequestDTO dto) {

        // 트레이닝 도메인 생성 및 저장
        Training training = Training.from(trainerId, dto);
        trainingMapper.createTraining(training);

        // 루틴 생성 및 저장
        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = dto.getRoutines();
        if (routines != null && !routines.isEmpty()) {
            for (TrainerTrainingCreateRequestDTO.RoutineDTO routineDto : routines) {
                Routine routine = Routine.createRoutine(training.getTrainingId(), routineDto);
                trainingRoutineMapper.createRoutine(routine);

                if (routine.getVideoUrl() != null && !routine.getVideoUrl().isEmpty()) {
                    trainingRoutineMapper.createRoutineVideo(routine.getRoutineId(), routine.getVideoUrl());
                }
            }
        }
    }
}
