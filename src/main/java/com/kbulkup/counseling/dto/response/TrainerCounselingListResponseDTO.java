package com.kbulkup.counseling.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerCounselingListResponseDTO {

    private String traineeName;
    private String traineeProfileUrl;
    private String roomId;
    private String status;

    @Setter
    private int unreadCount;
    private String trainingTitle;
    private String latestMessage;
    private LocalDateTime latestAt;
    private LocalDateTime expiresAt;
}
