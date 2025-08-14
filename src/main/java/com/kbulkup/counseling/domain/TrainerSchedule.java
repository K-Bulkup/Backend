package com.kbulkup.counseling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSchedule {

    private Long scheduleId;
    private Long trainerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAvailable;
    private LocalDateTime createdAt;

    public static TrainerSchedule createSchedule(Long trainerId, LocalDateTime startTime, LocalDateTime endTime, Boolean isAvailable) {
        return TrainerSchedule.builder()
                .trainerId(trainerId)
                .startTime(startTime)
                .endTime(endTime)
                .isAvailable(isAvailable)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
