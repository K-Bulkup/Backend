package com.kbulkup.statistics.service;

import com.kbulkup.statistics.dto.response.DailyRevenueDTO;
import com.kbulkup.statistics.dto.response.MonthlyRevenueDTO;
import com.kbulkup.statistics.dto.response.StatisticsTrainerRevenueResponseDTO;
import com.kbulkup.statistics.mapper.StatisticsTrainerRevenueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsTrainerRevenueServiceImpl implements StatisticsTrainerRevenueService {

    private final StatisticsTrainerRevenueMapper statisticsTrainerRevenueMapper;

    @Override
    public StatisticsTrainerRevenueResponseDTO getTrainerRevenueStatistics(Long trainerId, Long trainingId) {
        StatisticsTrainerRevenueResponseDTO totalRevenueData;

        if (trainingId != null) {
            // Get revenue for a specific training
            totalRevenueData = statisticsTrainerRevenueMapper.getTotalAndLast30DaysRevenueForTraining(trainerId, trainingId);
        } else {
            // Get overall trainer revenue
            totalRevenueData = statisticsTrainerRevenueMapper.getTotalAndLast30DaysRevenue(trainerId);
        }

        // 트레이닝별 누적 매출액 및 최근 30일 누적 매출액 조회
        List<StatisticsTrainerRevenueResponseDTO.TrainingRevenueDTO> trainingRevenueDetails = statisticsTrainerRevenueMapper.getTrainingRevenueDetails(trainerId);

        // 최종 DTO 구성
        return StatisticsTrainerRevenueResponseDTO.builder()
                .totalAccumulatedRevenue(totalRevenueData != null ? totalRevenueData.getTotalAccumulatedRevenue() : null)
                .last30DaysAccumulatedRevenue(totalRevenueData != null ? totalRevenueData.getLast30DaysAccumulatedRevenue() : null)
                .trainingRevenues(trainingRevenueDetails)
                .build();
    }

    @Override
    public List<DailyRevenueDTO> getDailyRevenueLast30Days(Long trainerId) {
        return statisticsTrainerRevenueMapper.getDailyRevenueLast30Days(trainerId);
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenue(Long trainerId) {
        return statisticsTrainerRevenueMapper.getMonthlyRevenue(trainerId);
    }

    @Override
    public List<DailyRevenueDTO> getDailyRevenueForTraining(Long trainerId, Long trainingId) {
        return statisticsTrainerRevenueMapper.getDailyRevenueForTraining(trainerId, trainingId);
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthlyRevenueForTraining(Long trainerId, Long trainingId) {
        return statisticsTrainerRevenueMapper.getMonthlyRevenueForTraining(trainerId, trainingId);
    }
}