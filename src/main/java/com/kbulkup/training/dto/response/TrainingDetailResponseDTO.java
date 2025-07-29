package com.kbulkup.training.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
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
    private List<RoutineResponseDTO> routines;

    @Getter
    @NoArgsConstructor
    public static class RoutineResponseDTO {
        private Long routineId;
        private String title;
        private boolean completed;
        private int rewardPoint;
        private LocalDateTime completedAt;
    }
}
