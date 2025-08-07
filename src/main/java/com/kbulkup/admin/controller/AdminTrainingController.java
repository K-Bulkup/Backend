package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;
import com.kbulkup.admin.service.training.AdminTrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/trainings")
public class AdminTrainingController {

    private final AdminTrainingService adminTrainingService;

    @GetMapping
    public ResponseEntity<List<AdminTrainingResponseDTO>> getAllTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getApprovedTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getApprovedTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getPendingTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getPendingTrainingsForAdmin();
        return ResponseEntity.ok(trainings);
    }

    @PostMapping("/{trainingId}/approve")
    public ResponseEntity<Void> approveTraining(@PathVariable Long trainingId) {
        adminTrainingService.approveTraining(trainingId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{trainingId}/reject")
    public ResponseEntity<Void> rejectTraining(@PathVariable Long trainingId) {
        adminTrainingService.rejectTraining(trainingId);
        return ResponseEntity.ok().build();
    }
}