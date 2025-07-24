package com.kbulkup.training.controller;

import com.kbulkup.training.dto.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainer/trainings")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/{trainerId}")
    public ResponseEntity<String> createTraining(@PathVariable Long trainerId,
                                                 @RequestBody TrainerTrainingCreateRequestDTO dto) {

        trainingService.createTraining(trainerId, dto);
        return ResponseEntity.ok("Training created successfully");
    }
}
