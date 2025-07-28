package com.kbulkup.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDetailResponseDTO {

    private String title;
    private String description;
    private int price;
    private String category;
    private String level;
    private int totalScore;
    private float averageRating;
    private float traineeCount;
    private float progress;
    private LocalDateTime completedAt;
    private List<RoutineDTO> routines;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoutineDTO {
        private Long routineId;
        private String name;
        private boolean completed;
        private int rewardPoint;
        private LocalDateTime completedAt;
    }
}
