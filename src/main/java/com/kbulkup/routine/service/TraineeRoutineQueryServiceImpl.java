package com.kbulkup.routine.service;

import com.kbulkup.routine.dto.TraineeRoutineDetailResponseDTO;
import com.kbulkup.routine.mapper.TraineeRoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeRoutineQueryServiceImpl implements TraineeRoutineQueryService {

    private final TraineeRoutineMapper traineeRoutineMapper;

    @Override
    public TraineeRoutineDetailResponseDTO getRoutineDetail(Long routineId) {
        return traineeRoutineMapper.findRoutineDetailById(routineId);
    }
}
