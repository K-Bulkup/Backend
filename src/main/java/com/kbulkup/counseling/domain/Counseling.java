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
public class Counseling {

    private Long counselingId;
    private Long userId;
    private Long trainerId;
    private Long trainingId;
    private String roomId;
    private String status;
    private String latestMessage;
    private LocalDateTime latestAt;
    private LocalDateTime startAt;
    private LocalDateTime expiresAt;

    public static Counseling createCounseling(Long traineeId, Long trainerId, Long trainingId, String roomId) {
        return Counseling.builder()
                .userId(traineeId)
                .trainerId(trainerId)
                .trainingId(trainingId)
                .roomId(roomId)
                .status(CounselingStatus.PROGRESS.getName())
                .startAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .build();
    }
}
