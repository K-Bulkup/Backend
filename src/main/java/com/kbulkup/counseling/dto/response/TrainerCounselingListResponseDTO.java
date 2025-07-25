package com.kbulkup.counseling.dto.response;

import com.kbulkup.counseling.domain.CounselingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerCounselingListResponseDTO {

    private String traineeName;
    private String traineeProfileUrl;
//    private CounselingStatus status;
    private String status;
    private String trainingTitle;
    private String latestMessage;
    private LocalDateTime latestAt;

}
