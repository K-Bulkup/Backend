package com.kbulkup.counseling.dto.response;

import com.kbulkup.counseling.domain.CounselingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerCounselingListResponseDTO {

    private String traineeName;
    private String traineeProfileUrl;
    //    private CounselingStatus status;
    private String roomId;
    private String status;

    @Setter
    private int unreadCount;
    private String trainingTitle;
    private String latestMessage;
    private LocalDateTime latestAt;

}
