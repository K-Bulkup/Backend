package com.kbulkup.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserStatisticsResponseDTO {
    private String dateLabel;
    private long signupCount;
}
