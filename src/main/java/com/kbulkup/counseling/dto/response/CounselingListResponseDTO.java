package com.kbulkup.counseling.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "상담방 목록 항목")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselingListResponseDTO {

    @ApiModelProperty(value = "상대 이름", example = "trainer_lee")
    private String opponentName;

    @ApiModelProperty(value = "상대 프로필 이미지 URL")
    private String opponentProfileUrl;

    @ApiModelProperty(value = "상담방 ID", example = "ROOM-abc123")
    private String roomId;

    @ApiModelProperty(value = "상태", example = "ACTIVE")
    private String status;

    @Setter
    @ApiModelProperty(value = "읽지 않은 메시지 수", example = "2")
    private int unreadCount;

    @ApiModelProperty(value = "트레이닝 제목", example = "아침 스트레칭 루틴")
    private String trainingTitle;

    @ApiModelProperty(value = "마지막 메시지")
    private String latestMessage;

    @ApiModelProperty(value = "마지막 메시지 시각", example = "2025-08-18T10:30:00")
    private LocalDateTime latestAt;

    @ApiModelProperty(value = "만료 시각", example = "2025-09-01T00:00:00")
    private LocalDateTime expiresAt;
}
