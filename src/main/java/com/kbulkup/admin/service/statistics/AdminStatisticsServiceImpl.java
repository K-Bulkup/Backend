package com.kbulkup.admin.service.statistics;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;
import com.kbulkup.admin.mapper.AdminStatisticsMapper;
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
    public List<AdminUserStatisticsResponseDTO> getUserSignupsByPeriod(String period) {
        switch (period.toLowerCase()) {
            case "daily":
                // 최근 30일 데이터 조회
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = endDate.minusDays(29); // 오늘 포함 30일
                return adminStatisticsMapper.findDailyUserSignups(startDate.format(DateTimeFormatter.ISO_DATE), endDate.format(DateTimeFormatter.ISO_DATE));
            case "weekly":
                return adminStatisticsMapper.findWeeklyUserSignups();
            case "monthly":
                return adminStatisticsMapper.findMonthlyUserSignups();
            case "yearly":
                return adminStatisticsMapper.findYearlyUserSignups();
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
