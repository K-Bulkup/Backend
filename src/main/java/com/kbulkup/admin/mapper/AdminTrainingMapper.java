package com.kbulkup.admin.mapper;

import com.kbulkup.admin.dto.AdminTrainingResponseDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AdminTrainingMapper {
    List<AdminTrainingResponseDto> findAllTrainings();
    List<AdminTrainingResponseDto> findApprovedTrainings();
    List<AdminTrainingResponseDto> findPendingTrainings();
}
