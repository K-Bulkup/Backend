package com.kbulkup.admin.service;

import com.kbulkup.admin.dto.AdminTrainingResponseDto;
import com.kbulkup.admin.mapper.AdminTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTrainingService {

    private final AdminTrainingMapper adminTrainingMapper;

    public List<AdminTrainingResponseDto> getAllTrainings() {
        return adminTrainingMapper.findAllTrainings();
    }

    public List<AdminTrainingResponseDto> getApprovedTrainings() {
        return adminTrainingMapper.findApprovedTrainings();
    }

    public List<AdminTrainingResponseDto> getPendingTrainingsForAdmin() {
        return adminTrainingMapper.findPendingTrainings();
    }

    // TODO: 강좌 승인/거절 로직 구현 필요
    public void approveTraining(Long trainingId) {
        // trainingRepository.findById(trainingId)... or adminTrainingMapper.updateApprovalStatus(trainingId, "APPROVED");
    }

    public void rejectTraining(Long trainingId) {
        // trainingRepository.findById(trainingId)... or adminTrainingMapper.updateApprovalStatus(trainingId, "REJECTED");
    }
}
