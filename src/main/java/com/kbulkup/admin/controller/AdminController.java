package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.AdminTrainingResponseDto;
import com.kbulkup.admin.service.AdminTrainingService;
import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.service.LoginContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final LoginContext loginContext;
    private final AdminTrainingService adminTrainingService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(@RequestBody LoginRequestDTO dto) {
        LoginRequestDTO adminLoginRequest = LoginRequestDTO.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .code(dto.getCode())
                .loginType(dto.getLoginType())
                .role("ADMIN") // role을 ADMIN으로 설정
                .build();
        LoginResponseDTO responseDTO = loginContext.executeLogin(adminLoginRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<AdminTrainingResponseDto>> getAllTrainings() {
        List<AdminTrainingResponseDto> trainings = adminTrainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/trainings/approved")
    public ResponseEntity<List<AdminTrainingResponseDto>> getApprovedTrainings() {
        List<AdminTrainingResponseDto> trainings = adminTrainingService.getApprovedTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/trainings/pending")
    public ResponseEntity<List<AdminTrainingResponseDto>> getPendingTrainings() {
        List<AdminTrainingResponseDto> pendingTrainings = adminTrainingService.getPendingTrainingsForAdmin();
        return ResponseEntity.ok(pendingTrainings);
    }

    @PostMapping("/trainings/{trainingId}/approve")
    public ResponseEntity<String> approveTraining(@PathVariable Long trainingId) {
        adminTrainingService.approveTraining(trainingId);
        return ResponseEntity.ok("강좌가 성공적으로 승인되었습니다.");
    }

    @PostMapping("/trainings/{trainingId}/reject")
    public ResponseEntity<String> rejectTraining(@PathVariable Long trainingId) {
        adminTrainingService.rejectTraining(trainingId);
        return ResponseEntity.ok("강좌가 거절되었습니다.");
    }
}
