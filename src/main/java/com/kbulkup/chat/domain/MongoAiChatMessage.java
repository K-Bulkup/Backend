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
@Document(collection = "ai_chat_messages")
public class MongoAiChatMessage {

    @Id
    private String id;
    private String userId;
    private String message;
    private String role;
    private LocalDateTime sendAt;

    public static MongoAiChatMessage create(String userId, String message, String role) {
        return MongoAiChatMessage.builder().
                userId(userId).
                message(message).
                role(role).
                sendAt(LocalDateTime.now()).
                build();
    }
}
