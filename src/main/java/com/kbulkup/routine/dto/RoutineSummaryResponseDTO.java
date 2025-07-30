package com.kbulkup.routine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineSummaryResponseDTO {
    private Long routineId;
    private String title;
    private boolean completed;
    private int rewardPoint;
    private LocalDateTime completedAt;
}
