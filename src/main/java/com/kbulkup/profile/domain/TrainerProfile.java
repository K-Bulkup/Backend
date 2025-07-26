package com.kbulkup.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfile {

    private Long trainerId;
    private float totalAverageRating;
    private int totalTraineeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
