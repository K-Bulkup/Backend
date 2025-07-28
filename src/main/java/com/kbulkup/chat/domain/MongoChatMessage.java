package com.kbulkup.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class MongoChatMessage {

    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String receiverId;
    private String message;
    private boolean isRead;
    private LocalDateTime sendAt;

    public static MongoChatMessage create(ChatMessage chatMessage, String receiverId) {
        return MongoChatMessage.builder()
                .roomId(chatMessage.getRoomId())
                .senderId(chatMessage.getSenderId())
                .receiverId(receiverId)
                .message(chatMessage.getMessage())
                .isRead(false)
                .sendAt(chatMessage.getSendAt())
                .build();
    }

}