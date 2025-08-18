package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;
import com.kbulkup.admin.service.training.AdminTrainingService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Admin Training", description = "관리자 트레이닝 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/trainings")
public class AdminTrainingController {

    private final AdminTrainingService adminTrainingService;

    @ApiOperation(value = "전체 트레이닝 목록", notes = "등록된 모든 트레이닝을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<AdminTrainingResponseDTO>> getAllTrainings() {
        return ResponseEntity.ok(adminTrainingService.getAllTrainings());
    }

    @ApiOperation(value = "승인된 트레이닝 목록", notes = "상태가 APPROVED인 트레이닝만 조회합니다.")
    @GetMapping("/approved")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getApprovedTrainings() {
        return ResponseEntity.ok(adminTrainingService.getApprovedTrainings());
    }

    @ApiOperation(value = "대기중 트레이닝 목록", notes = "상태가 PENDING인 트레이닝만 조회합니다.")
    @GetMapping("/pending")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getPendingTrainings() {
        return ResponseEntity.ok(adminTrainingService.getPendingTrainingsForAdmin());
    }

    @ApiOperation(value = "트레이닝 승인", notes = "PENDING → APPROVED 처리")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true,
            dataType = "long", paramType = "path")
    @PostMapping("/{trainingId}/approve")
    public ResponseEntity<Void> approveTraining(@PathVariable Long trainingId) {
        adminTrainingService.approveTraining(trainingId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "트레이닝 반려", notes = "PENDING → REJECTED 처리")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID", required = true,
            dataType = "long", paramType = "path")
    @PostMapping("/{trainingId}/reject")
    public ResponseEntity<Void> rejectTraining(@PathVariable Long trainingId) {
        adminTrainingService.rejectTraining(trainingId);
        return ResponseEntity.ok().build();
    }
}
