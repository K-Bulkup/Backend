package com.kbulkup.admin.service;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;

import java.util.List;

public interface AdminService {
    List<AdminTrainingResponseDto> getAllTrainings();
    List<AdminTrainingResponseDto> getApprovedTrainings();
    List<AdminTrainingResponseDto> getPendingTrainingsForAdmin();
    void approveTraining(Long trainingId);
    void rejectTraining(Long trainingId);

    // User management methods
    void createUser(SignupRequestDTO requestDto);
    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> getAllUsers();
    void updateUser(Long userId, UserRequestDTO requestDto);
    void deleteUser(Long userId);
}
