package com.kbulkup.admin.service.training;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;

import java.util.List;

public interface AdminTrainingService {
    List<AdminTrainingResponseDTO> getAllTrainings();
    List<AdminTrainingResponseDTO> getApprovedTrainings();
    List<AdminTrainingResponseDTO> getPendingTrainingsForAdmin();
    void approveTraining(Long trainingId);
    void rejectTraining(Long trainingId);
}
