package com.kbulkup.admin.mapper;

import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdminTrainingMapper {
    List<AdminTrainingResponseDto> findAllTrainings();
    List<AdminTrainingResponseDto> findApprovedTrainings();
    List<AdminTrainingResponseDto> findPendingTrainings();
    void updateTrainingApprovalStatus(@Param("trainingId") Long trainingId, @Param("status") String status);
}
