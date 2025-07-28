package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingDetailResponseDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.service.TraineeTrainingService;
import com.kbulkup.training.service.TrainingSearchService;
import com.kbulkup.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingSearchService trainingSearchService;
    private final TraineeTrainingService traineeTrainingService;

    // [트레이너] 트레이닝 생성
    @PostMapping("/trainer/trainings/{trainerId}")
    public ResponseEntity<String> createTraining(@PathVariable Long trainerId,
                                                 @RequestBody TrainerTrainingCreateRequestDTO dto) {
        trainingService.createTraining(trainerId, dto);
        return ResponseEntity.ok("Training created successfully");
    }

    // [공용] 트레이닝 검색
    @GetMapping("/trainings/search")
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @ModelAttribute TrainingSearchListRequestDTO dto) {
        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(dto));
    }

    // [수강생] 트레이닝 상세 조회
    @GetMapping("/trainee/trainings/{trainingId}")
    public CustomResponse<TrainingDetailResponseDTO> getTrainingDetail(
            @PathVariable Long trainingId,
            @RequestParam Long userId) {

        // 진행률 먼저 업데이트 → 최신 진행률 유지
        traineeTrainingService.updateTrainingProgress(trainingId, userId);

        // 트레이닝 상세 조회
        TrainingDetailResponseDTO response = traineeTrainingService.getTrainingDetail(trainingId, userId);

        return CustomResponse.success(ResponseCode.SUCCESS, response);
    }
}
