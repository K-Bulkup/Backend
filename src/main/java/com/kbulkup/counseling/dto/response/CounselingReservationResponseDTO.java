package com.kbulkup.counseling.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CounselingReservationResponseDTO {

    private Long reservationId;
    private Long traineeId;
    private Long trainerId;
    private Long trainingId;
    private Long scheduleId;
    private String status;
    private String roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String trainerName;
    private String traineeName;
    private String trainingTitle;
    private LocalDateTime createdAt;
}
