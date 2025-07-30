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

    private String userName;
    private String userProfileUrl;
    private LocalDateTime expiresAt;

    public static CounselingDetailResponseDTO create(String userName, String userProfileUrl, LocalDateTime expiresAt) {
        return CounselingDetailResponseDTO.builder()
                .userName(userName)
                .userProfileUrl(userProfileUrl)
                .expiresAt(expiresAt)
                .build();
    }
}
