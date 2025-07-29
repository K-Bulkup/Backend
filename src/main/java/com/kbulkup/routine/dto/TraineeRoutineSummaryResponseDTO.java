package com.kbulkup.routine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TraineeRoutineSummaryResponseDTO {

    private String title;
    private String description;
    private int price;
    private String category;
    private String level;
    private int totalScore;
    private float averageRating;
    private int traineeCount;
    private float progress;

    private List<RoutineSummaryResponseDTO> routines;

    @Builder
    private TraineeRoutineSummaryResponseDTO(String title, String description, int price, String category,
                                             String level, int totalScore, float averageRating,
                                             int traineeCount, float progress,
                                             List<RoutineSummaryResponseDTO> routines) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.level = level;
        this.totalScore = totalScore;
        this.averageRating = averageRating;
        this.traineeCount = traineeCount;
        this.progress = progress;
        this.routines = routines;
    }

    /** 팩토리 메소드 추가 */
    public static TraineeRoutineSummaryResponseDTO of(String title, String description, int price, String category,
                                                      String level, int totalScore, float averageRating,
                                                      int traineeCount, float progress,
                                                      List<RoutineSummaryResponseDTO> routines) {
        return TraineeRoutineSummaryResponseDTO.builder()
                .title(title)
                .description(description)
                .price(price)
                .category(category)
                .level(level)
                .totalScore(totalScore)
                .averageRating(averageRating)
                .traineeCount(traineeCount)
                .progress(progress)
                .routines(routines)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class RoutineSummaryResponseDTO {

        private Long routineId;
        private String title;
        private boolean completed;
        private int rewardPoint;
        private LocalDateTime completedAt;

        @Builder
        private RoutineSummaryResponseDTO(Long routineId, String title, boolean completed,
                                          int rewardPoint, LocalDateTime completedAt) {
            this.routineId = routineId;
            this.title = title;
            this.completed = completed;
            this.rewardPoint = rewardPoint;
            this.completedAt = completedAt;
        }

        /** 루틴용 팩토리 메소드 추가 */
        public static RoutineSummaryResponseDTO of(Long routineId, String title, boolean completed,
                                                   int rewardPoint, LocalDateTime completedAt) {
            return RoutineSummaryResponseDTO.builder()
                    .routineId(routineId)
                    .title(title)
                    .completed(completed)
                    .rewardPoint(rewardPoint)
                    .completedAt(completedAt)
                    .build();
        }
    }
}
