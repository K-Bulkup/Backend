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
    private String quizType;

    public static RoutineSummaryResponseDTO of(
            Long routineId, String title, boolean completed, int rewardPoint, LocalDateTime completedAt, String routineType, String quizType
    ) {
        return RoutineSummaryResponseDTO.builder()
                .routineId(routineId)
                .title(title)
                .completed(completed)
                .rewardPoint(rewardPoint)
                .completedAt(completedAt)
                .routineType(routineType)
                .quizType(quizType) // quizType을 DTO에 포함
                .build();
    }
}
