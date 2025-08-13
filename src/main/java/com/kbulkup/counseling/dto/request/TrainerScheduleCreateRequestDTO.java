package com.kbulkup.counseling.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TrainerScheduleCreateRequestDTO {

    private List<TimeSlotDTO> timeSlots;

    @Getter
    public static class TimeSlotDTO {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
