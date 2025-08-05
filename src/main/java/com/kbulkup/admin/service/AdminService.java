package com.kbulkup.admin.service;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;

import java.util.List;

public interface AdminService {
    List<AdminTrainingResponseDto> getAllTrainings();
    List<AdminTrainingResponseDto> getApprovedTrainings();
    List<AdminTrainingResponseDto> getPendingTrainingsForAdmin();
    void approveTraining(Long trainingId);
    void rejectTraining(Long trainingId);
}
