package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;
import com.kbulkup.admin.service.training.AdminTrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Admin Training", description = "관리자 트레이닝 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/trainings")
public class AdminTrainingController {

    private final AdminTrainingService adminTrainingService;

    @ApiOperation(value = "전체 트레이닝 목록", notes = "등록된 모든 트레이닝을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping
    public ResponseEntity<List<AdminTrainingResponseDTO>> getAllTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @ApiOperation(value = "승인된 트레이닝 목록", notes = "상태가 APPROVED 인 트레이닝만 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/approved")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getApprovedTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getApprovedTrainings();
        return ResponseEntity.ok(trainings);
    }

    @ApiOperation(value = "대기중 트레이닝 목록", notes = "상태가 PENDING 인 트레이닝만 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/pending")
    public ResponseEntity<List<AdminTrainingResponseDTO>> getPendingTrainings() {
        List<AdminTrainingResponseDTO> trainings = adminTrainingService.getPendingTrainingsForAdmin();
        return ResponseEntity.ok(trainings);
    }

    @ApiOperation(value = "트레이닝 승인", notes = "대기중(PENDING) 트레이닝을 승인(APPROVED) 처리합니다.")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID",
            required = true, dataType = "long", paramType = "path", example = "101")
    @PostMapping("/{trainingId}/approve")
    public ResponseEntity<Void> approveTraining(@PathVariable Long trainingId) {
        adminTrainingService.approveTraining(trainingId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "트레이닝 반려", notes = "대기중(PENDING) 트레이닝을 반려(REJECTED) 처리합니다.")
    @ApiImplicitParam(name = "trainingId", value = "트레이닝 ID",
            required = true, dataType = "long", paramType = "path", example = "101")
    @PostMapping("/{trainingId}/reject")
    public ResponseEntity<Void> rejectTraining(@PathVariable Long trainingId) {
        adminTrainingService.rejectTraining(trainingId);
        return ResponseEntity.ok().build();
    }
}
