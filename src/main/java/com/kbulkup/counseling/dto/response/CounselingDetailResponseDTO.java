package com.kbulkup.counseling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingDetailResponseDTO {

    private String opponentUserName;
    private String opponentProfileUrl;
    private String myProfileUrl;
    private String status;
    private LocalDateTime expiresAt;

    public static CounselingDetailResponseDTO create(String opponentUserName, String opponentProfileUrl, String myProfileUrl, String status, LocalDateTime expiresAt) {
        return CounselingDetailResponseDTO.builder()
                .opponentUserName(opponentUserName)
                .opponentProfileUrl(opponentProfileUrl)
                .myProfileUrl(myProfileUrl)
                .status(status)
                .expiresAt(expiresAt)
                .build();
    }
}
