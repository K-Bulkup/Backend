package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.response.DailyRevenueDTO;
import com.kbulkup.statistics.dto.response.MonthlyRevenueDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;

import java.util.List;

public interface StatisticsTrainerRevenueService {
    StatisticsTrainerRevenueResponseDTO getTrainerRevenueStatistics(Long trainerId, Long trainingId);

    List<DailyRevenueDTO> getDailyRevenueLast30Days(Long trainerId);

    List<MonthlyRevenueDTO> getMonthlyRevenue(Long trainerId);

    List<DailyRevenueDTO> getDailyRevenueForTraining(Long trainerId, Long trainingId);

    List<MonthlyRevenueDTO> getMonthlyRevenueForTraining(Long trainerId, Long trainingId);
}