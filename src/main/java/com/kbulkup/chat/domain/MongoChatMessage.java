package com.kbulkup.chat.domain;

import com.kbulkup.chat.dto.ChatMessageDTO;
import lombok.*;
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
    @Setter
    private boolean isRead;
    private LocalDateTime sendAt;

    public static MongoChatMessage create(ChatMessageDTO chatMessageDTO, String receiverId) {
        return MongoChatMessage.builder()
                .roomId(chatMessageDTO.getRoomId())
                .senderId(chatMessageDTO.getSenderId())
                .receiverId(receiverId)
                .message(chatMessageDTO.getMessage())
                .isRead(false)
                .sendAt(chatMessageDTO.getSendAt())
                .build();
    }

}