package com.kbulkup.admin.service;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;
import com.kbulkup.admin.mapper.AdminTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminTrainingMapper adminTrainingMapper;

    @Override
    public List<AdminTrainingResponseDto> getAllTrainings() {
        return adminTrainingMapper.findAllTrainings();
    }

    @Override
    public List<AdminTrainingResponseDto> getApprovedTrainings() {
        return adminTrainingMapper.findApprovedTrainings();
    }

    @Override
    public List<AdminTrainingResponseDto> getPendingTrainingsForAdmin() {
        return adminTrainingMapper.findPendingTrainings();
    }

    @Override
    @Transactional
    public void approveTraining(Long trainingId) {
        adminTrainingMapper.updateTrainingApprovalStatus(trainingId, "승인");
    }

    @Override
    @Transactional
    public void rejectTraining(Long trainingId) {
        adminTrainingMapper.updateTrainingApprovalStatus(trainingId, "거부");
    }
}
