package com.kbulkup.statistics.service;

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
    public StatisticsTrainerRevenueResponseDTO getTrainerRevenueStatistics(Long trainerId) {
        // 전체 누적 매출액 및 최근 30일 누적 매출액 조회
        StatisticsTrainerRevenueResponseDTO totalRevenueData = statisticsTrainerRevenueMapper.getTotalAndLast30DaysRevenue(trainerId);

        // 트레이닝별 누적 매출액 및 최근 30일 누적 매출액 조회
        List<StatisticsTrainerRevenueResponseDTO.TrainingRevenueDTO> trainingRevenueDetails = statisticsTrainerRevenueMapper.getTrainingRevenueDetails(trainerId);

        // 최종 DTO 구성
        return StatisticsTrainerRevenueResponseDTO.builder()
                .totalAccumulatedRevenue(totalRevenueData != null ? totalRevenueData.getTotalAccumulatedRevenue() : null)
                .last30DaysAccumulatedRevenue(totalRevenueData != null ? totalRevenueData.getLast30DaysAccumulatedRevenue() : null)
                .trainingRevenues(trainingRevenueDetails)
                .build();
    }
}