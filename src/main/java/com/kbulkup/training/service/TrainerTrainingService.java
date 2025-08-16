package com.kbulkup.training.service;

import com.kbulkup.training.dto.request.TrainerTrainingDetailRequestDTO;
import com.kbulkup.training.dto.response.RoutineCategoryResponseDTO;
import com.kbulkup.training.dto.response.TrainerTrainingDetailResponseDTO;
import com.kbulkup.training.mapper.TrainerTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerTrainingService {

    private final TrainerTrainingMapper trainerTrainingMapper;

    public TrainerTrainingDetailResponseDTO getTrainerTrainingDetail(TrainerTrainingDetailRequestDTO dto) {
        return trainerTrainingMapper.selectTrainerTrainingDetail(dto);
    }

    public List<Map<String, String>> getTrainerTrainingRoutines(Long trainingId) {
        return trainerTrainingMapper.selectRoutineRows(trainingId).stream()
                .map(dto -> Map.of(
                        "category", dto.getCategory(),        // 한글 그대로
                        "routineTitle", dto.getRoutineTitle(),
                        "quizType",dto.getQuizType()
                ))
                .collect(Collectors.toList());
    }
}
