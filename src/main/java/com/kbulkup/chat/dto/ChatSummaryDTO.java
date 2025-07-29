package com.kbulkup.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatSummaryDTO {

    private String roomId;
    private String lastMessage;
    private String receiverId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendAt;
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
