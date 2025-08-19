package com.kbulkup.statistics.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApiModel(description = "일별 매출 항목")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRevenueDTO {
    @ApiModelProperty("날짜")
    private LocalDate date;
    @ApiModelProperty("매출 금액")
    private BigDecimal revenue;
}
