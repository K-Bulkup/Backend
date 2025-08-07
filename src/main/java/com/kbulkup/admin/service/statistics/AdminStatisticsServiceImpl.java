package com.kbulkup.admin.service.statistics;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.dto.response.AdminUserSummaryStatsResponseDTO;
import com.kbulkup.admin.mapper.AdminStatisticsMapper;
import com.kbulkup.common.exception.AdminException;
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

    private final AdminStatisticsMapper adminStatisticsMapper;

    @Override
    public List<AdminUserStatisticsResponseDTO> getUserSignupsByPeriod(String period, String role) {
        String finalRole = (role == null || role.isEmpty()) ? "all" : role.toUpperCase();

        switch (period.toLowerCase()) {
            case "daily":
                LocalDate endDate = LocalDate.now().plusDays(1);
                LocalDate startDate = endDate.minusDays(30);
                return adminStatisticsMapper.findDailyUserSignups(startDate.format(DateTimeFormatter.ISO_DATE), endDate.format(DateTimeFormatter.ISO_DATE), finalRole);
            case "weekly":
                return adminStatisticsMapper.findWeeklyUserSignups(finalRole);
            case "monthly":
                return adminStatisticsMapper.findMonthlyUserSignups(finalRole);
            case "yearly":
                return adminStatisticsMapper.findYearlyUserSignups(finalRole);
            case "all_time":
                return adminStatisticsMapper.findAllTimeUserSignups(finalRole);
            default:
                throw new AdminException(ResponseCode.ADMIN_INVALID_DATE_VALUE);
        }
    }

    @Override
    public AdminUserSummaryStatsResponseDTO getUserSummaryStatistics() {
        return adminStatisticsMapper.findUserSummaryStats();
    }
}