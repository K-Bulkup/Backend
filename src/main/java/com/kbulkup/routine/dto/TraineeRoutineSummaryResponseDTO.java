package com.kbulkup.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
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
    // 트레이너 정보 추가
    private String trainerNickname;
    private String trainerProfileUrl;
    private Long trainerId;

    // 루틴 타입별 Map
    private Map<String, List<RoutineSummaryResponseDTO>> routines;

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class RoutineSummaryResponseDTO {
        private Long routineId;
        private String title;
        private boolean completed;
        private int rewardPoint;
        private LocalDateTime completedAt;
    }
}
