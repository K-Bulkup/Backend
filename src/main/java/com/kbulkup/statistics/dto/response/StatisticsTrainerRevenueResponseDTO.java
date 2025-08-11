package com.kbulkup.statistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsTrainerRevenueResponseDTO {
    private BigDecimal totalAccumulatedRevenue;
    private BigDecimal last30DaysAccumulatedRevenue;
    private List<TrainingRevenueDTO> trainingRevenues;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TrainingRevenueDTO {
        private Long trainingId;
        private String trainingTitle;
        private BigDecimal accumulatedRevenue;
        private BigDecimal last30DaysAccumulatedRevenue;
    }
}