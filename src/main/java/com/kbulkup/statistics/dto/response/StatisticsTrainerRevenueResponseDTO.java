package com.kbulkup.statistics.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "트레이너 매출 통계 요약")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsTrainerRevenueResponseDTO {
    @ApiModelProperty("총 누적 매출")
    private BigDecimal totalAccumulatedRevenue;
    @ApiModelProperty("최근 30일 누적 매출")
    private BigDecimal last30DaysAccumulatedRevenue;
    @ApiModelProperty("트레이닝별 매출 목록")
    private List<TrainingRevenueDTO> trainingRevenues;

    @ApiModel(description = "트레이닝별 매출 요약")
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TrainingRevenueDTO {
        @ApiModelProperty("트레이닝 ID")
        private Long trainingId;
        @ApiModelProperty("트레이닝 제목")
        private String trainingTitle;
        @ApiModelProperty("누적 매출")
        private BigDecimal accumulatedRevenue;
        @ApiModelProperty("최근 30일 누적 매출")
        private BigDecimal last30DaysAccumulatedRevenue;
    }
}
