package com.kbulkup.admin.controller;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;
import com.kbulkup.admin.service.AdminService;
import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.service.LoginContext;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final LoginContext loginContext;
    private final AdminService adminService;

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
        List<AdminTrainingResponseDto> trainings = adminService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/trainings/approved")
    public ResponseEntity<List<AdminTrainingResponseDto>> getApprovedTrainings() {
        List<AdminTrainingResponseDto> trainings = adminService.getApprovedTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/trainings/pending")
    public ResponseEntity<List<AdminTrainingResponseDto>> getPendingTrainings() {
        List<AdminTrainingResponseDto> pendingTrainings = adminService.getPendingTrainingsForAdmin();
        return ResponseEntity.ok(pendingTrainings);
    }

    @PostMapping("/trainings/{trainingId}/approve")
    public ResponseEntity<String> approveTraining(@PathVariable Long trainingId) {
        adminService.approveTraining(trainingId);
        return ResponseEntity.ok("강좌가 성공적으로 승인되었습니다.");
    }

    @PostMapping("/trainings/{trainingId}/reject")
    public ResponseEntity<String> rejectTraining(@PathVariable Long trainingId) {
        adminService.rejectTraining(trainingId);
        return ResponseEntity.ok("강좌가 거절되었습니다.");
    }

    // User Management Endpoints
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody SignupRequestDTO requestDto) {
        adminService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO user = adminService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO requestDto) {
        adminService.updateUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
