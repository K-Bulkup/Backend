package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;

public interface StatisticsTrainerRevenueService {
    StatisticsTrainerRevenueResponseDTO getTrainerRevenueStatistics(Long trainerId);
}