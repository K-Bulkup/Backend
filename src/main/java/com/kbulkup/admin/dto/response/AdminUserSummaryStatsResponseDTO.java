package com.kbulkup.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserSummaryStatsResponseDTO {
    private long totalUsers;
    private long totalTrainers;
    private long totalTrainees;
}
