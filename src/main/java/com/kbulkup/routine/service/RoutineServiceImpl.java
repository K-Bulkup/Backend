package com.kbulkup.routine.service;

import com.kbulkup.routine.domain.Routine;
import com.kbulkup.routine.mapper.RoutineMapper;
import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineMapper mapper;

    @Override
    @Transactional
    public void createRoutines(Long trainingId, List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines) {
        if (routines == null || routines.isEmpty()) {
            return;
        }

        for (TrainerTrainingCreateRequestDTO.RoutineDTO routineDto : routines) {
            // 1. DTO와 trainingId로 Routine 도메인 객체를 생성합니다.
            Routine routine = Routine.create(trainingId, routineDto);

            // 2. routines 테이블에 기본 정보를 저장합니다.
            //    이때 useGeneratedKeys에 의해 routine 객체에 새로 생성된 routine_id가 담깁니다.
            mapper.createRoutine(routine);

            // 3. 비디오 URL이 있으면 RoutineVideos 테이블에 추가로 저장합니다.
            if (routine.getVideoUrl() != null && !routine.getVideoUrl().isEmpty()) {
                mapper.createRoutineVideo(routine.getRoutineId(), routine.getVideoUrl());
            }
        }
    }
}
