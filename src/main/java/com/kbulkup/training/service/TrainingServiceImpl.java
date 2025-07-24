package com.kbulkup.training.service;

import com.kbulkup.routine.service.RoutineService;
import com.kbulkup.training.domain.Training;
import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingMapper mapper;
    private final RoutineService routineService;


    @Override
    @Transactional
    public void createTraining(Long trainerId, TrainerTrainingCreateRequestDTO dto) {
        Training training = Training.create(trainerId, dto);

        // DB 저장
        mapper.create(training);

        // 루틴 생성
        routineService.createRoutines(training.getTrainingId(), dto.getRoutines());
    }
}
