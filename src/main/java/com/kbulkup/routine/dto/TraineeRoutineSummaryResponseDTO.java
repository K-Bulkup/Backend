package com.kbulkup.routine.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
