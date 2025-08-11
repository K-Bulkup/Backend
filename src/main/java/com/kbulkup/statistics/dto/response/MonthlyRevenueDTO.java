package com.kbulkup.statistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRevenueDTO {
    private String month; // e.g., "2023-10"
    private BigDecimal revenue;
}
