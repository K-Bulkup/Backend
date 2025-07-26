package com.kbulkup.training.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.dto.request.TrainingSearchListRequestDTO;
import com.kbulkup.training.dto.response.TrainingSearchListResponseDTO;
import com.kbulkup.training.service.TrainingSearchService;
import com.kbulkup.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/trainings")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingSearchService trainingSearchService;

    // [트레이너] 트레이닝 생성
    @PostMapping("/{trainerId}")
    public ResponseEntity<String> createTraining(@PathVariable Long trainerId,
                                                 @RequestBody TrainerTrainingCreateRequestDTO dto) {

        trainingService.createTraining(trainerId, dto);
        return ResponseEntity.ok("Training created successfully");
    }

    // [공용] 트레이닝 검색
    @GetMapping("/api/trainings/search")  // 절대 경로 지정
    public CustomResponse<List<TrainingSearchListResponseDTO>> searchTrainings(
            @RequestParam String keyword) {
        TrainingSearchListRequestDTO dto = new TrainingSearchListRequestDTO();
        dto.setKeyword(keyword);
        return CustomResponse.success(ResponseCode.SUCCESS, trainingSearchService.searchTrainings(dto));
    }

}
