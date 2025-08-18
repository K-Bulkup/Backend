package com.kbulkup.statistics.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.math.BigDecimal;

@ApiModel(description = "월별 매출 항목")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRevenueDTO {
    @ApiModelProperty("월 (YYYY-MM)")
    private String month;
    @ApiModelProperty("매출 금액")
    private BigDecimal revenue;
}
