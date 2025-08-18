package com.kbulkup.counseling.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "상담방 상세 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingDetailResponseDTO {

    @ApiModelProperty(value = "상대 사용자 이름", example = "trainer_lee")
    private String opponentUserName;

    @ApiModelProperty(value = "상대 프로필 이미지 URL")
    private String opponentProfileUrl;

    @ApiModelProperty(value = "나의 프로필 이미지 URL")
    private String myProfileUrl;

    @ApiModelProperty(value = "상태", example = "ACTIVE")
    private String status;

    @ApiModelProperty(value = "만료 시각(ISO-8601)", example = "2025-09-01T00:00:00")
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
