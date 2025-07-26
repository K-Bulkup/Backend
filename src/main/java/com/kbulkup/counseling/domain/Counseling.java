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
//    private CounselingStatus status;
    private String status;
    private String latestMessage;
    private LocalDateTime latestAt;
    private LocalDateTime startAt;
    private LocalDateTime expiresAt;
}
