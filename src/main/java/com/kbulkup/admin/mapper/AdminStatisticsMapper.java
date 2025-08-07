package com.kbulkup.admin.mapper;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.dto.response.AdminUserSummaryStatsResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminStatisticsMapper {
    List<AdminUserStatisticsResponseDTO> findDailyUserSignups(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("roleName") String roleName);
    List<AdminUserStatisticsResponseDTO> findWeeklyUserSignups(@Param("roleName") String roleName);
    List<AdminUserStatisticsResponseDTO> findMonthlyUserSignups(@Param("roleName") String roleName);
    List<AdminUserStatisticsResponseDTO> findYearlyUserSignups(@Param("roleName") String roleName);

    AdminUserSummaryStatsResponseDTO findUserSummaryStats();
    long countUsersByRole(@Param("roleName") String roleName);
    List<AdminUserStatisticsResponseDTO> findAllTimeUserSignups(@Param("roleName") String roleName);
}