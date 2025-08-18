package com.kbulkup.chat.dto;

import com.kbulkup.chat.domain.MongoAiChatMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "AI 채팅 이력 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatHistoryResponseDTO {

    @ApiModelProperty(value = "AI 채팅 메시지 목록")
    private List<MongoAiChatMessage> chatMessages;

    @ApiModelProperty(value = "남은 AI 채팅 가능 횟수")
    private int remainingChats;

    public static AiChatHistoryResponseDTO create(List<MongoAiChatMessage> chatMessages, int remainingChats) {
        return AiChatHistoryResponseDTO.builder()
                .chatMessages(chatMessages)
                .remainingChats(remainingChats)
                .build();
    }
}
