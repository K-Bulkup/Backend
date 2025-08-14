package com.kbulkup.counseling.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateRequestDTO {

    private Long trainerId;
    private Long trainingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
