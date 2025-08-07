package com.kbulkup.admin.mapper;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminStatisticsMapper {
    List<AdminUserStatisticsResponseDTO> findDailyUserSignups(@Param("startDate") String startDate, @Param("endDate") String endDate);
    List<AdminUserStatisticsResponseDTO> findWeeklyUserSignups();
    List<AdminUserStatisticsResponseDTO> findMonthlyUserSignups();
    List<AdminUserStatisticsResponseDTO> findYearlyUserSignups();
}
