package com.kbulkup.admin.mapper;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdminTrainingMapper {
    List<AdminTrainingResponseDTO> findAllTrainings();
    List<AdminTrainingResponseDTO> findApprovedTrainings();
    List<AdminTrainingResponseDTO> findPendingTrainings();
    void updateTrainingApprovalStatus(@Param("trainingId") Long trainingId, @Param("status") String status);
}
