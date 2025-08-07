package com.kbulkup.admin.service.training;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;
import com.kbulkup.admin.mapper.AdminTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTrainingServiceImpl implements AdminTrainingService {

    private final AdminTrainingMapper adminTrainingMapper;

    @Override
    public List<AdminTrainingResponseDTO> getAllTrainings() {
        return adminTrainingMapper.findAllTrainings();
    }

    @Override
    public List<AdminTrainingResponseDTO> getApprovedTrainings() {
        return adminTrainingMapper.findApprovedTrainings();
    }

    @Override
    public List<AdminTrainingResponseDTO> getPendingTrainingsForAdmin() {
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
