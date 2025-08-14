package com.kbulkup.counseling.dto.response;

import com.kbulkup.counseling.domain.TrainerSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerScheduleResponseDTO {

    private Long scheduleId;
    private Long trainerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAvailable;
    private LocalDateTime createdAt;

    public static TrainerScheduleResponseDTO createDTO(TrainerSchedule schedule) {
        return TrainerScheduleResponseDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .trainerId(schedule.getTrainerId())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .isAvailable(schedule.getIsAvailable())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
