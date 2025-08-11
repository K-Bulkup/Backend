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
public class CounselingReservation {

    private Long reservationId;
    private Long traineeId;
    private Long trainerId;
    private Long trainingId;
    private Long scheduleId;
    private String status;
    private String roomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CounselingReservation createReservation(Long traineeId, Long trainerId, Long trainingId, Long scheduleId, String roomId) {
        return CounselingReservation.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingId(trainingId)
                .scheduleId(scheduleId)
                .status(ReservationStatus.RESERVED.getName())
                .roomId(roomId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
