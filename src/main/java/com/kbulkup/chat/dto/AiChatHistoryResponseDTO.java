package com.kbulkup.chat.dto;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatHistoryResponseDTO {

    private List<MongoAiChatMessage> chatMessages;
    private int remainingChats;

    public static AiChatHistoryResponseDTO create(List<MongoAiChatMessage> chatMessages, int remainingChats) {
        return AiChatHistoryResponseDTO.builder()
                .chatMessages(chatMessages)
                .remainingChats(remainingChats)
                .build();
    }
}
