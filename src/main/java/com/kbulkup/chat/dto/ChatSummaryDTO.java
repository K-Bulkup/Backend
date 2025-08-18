package com.kbulkup.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "채팅방 요약")
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatSummaryDTO {

    @ApiModelProperty(value = "채팅방 ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "마지막 메시지(개인정보 포함 가능)")
    private String lastMessage;

    @ApiModelProperty(value = "수신자 사용자 ID", required = true)
    private String receiverId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(value = "마지막 메시지 시각")
    private LocalDateTime sendAt;

    @ApiModelProperty(value = "읽지 않은 메시지 수", example = "0")
    private int unreadCount;

    public static ChatSummaryDTO create(ChatMessageDTO dto, int unreadCount, String receiverId) {
        return ChatSummaryDTO.builder()
                .roomId(dto.getRoomId())
                .lastMessage(dto.getMessage())
                .receiverId(receiverId)
                .sendAt(dto.getSendAt())
                .unreadCount(unreadCount)
                .build();
    }
}
