package com.kbulkup.routine.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSummaryResponseDTO {
    private Long routineId;
    private String title;
    private boolean completed;
    private int rewardPoint;
    private LocalDateTime completedAt;
    private String routineType;
}
