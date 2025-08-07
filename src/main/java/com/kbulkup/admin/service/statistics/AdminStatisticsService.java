package com.kbulkup.admin.service.statistics;

import com.kbulkup.admin.dto.response.AdminUserStatisticsResponseDTO;

import java.util.List;

public interface AdminStatisticsService {
    List<AdminUserStatisticsResponseDTO> getUserSignupsByPeriod(String period);
}
