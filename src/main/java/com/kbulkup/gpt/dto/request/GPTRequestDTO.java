package com.kbulkup.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbulkup.gpt.dto.common.MessageDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTRequestDTO {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<MessageDTO> messages;

    @JsonProperty("max_tokens")
    private int maxTokens;

    // ✅ 텍스트 요청만 처리하는 팩토리 메서드
    public static GPTRequestDTO createOnlyText(String model, String role, String text, int maxTokens) {
        return GPTRequestDTO.builder()
                .model(model)
                .messages(List.of(MessageDTO.createOnlyText(role, text)))
                .maxTokens(maxTokens)
                .build();
    }

    // ✅ 텍스트 + 이미지 요청 처리 팩토리 메서드
    public static GPTRequestDTO createWithTextAndImage(String model, String role, String text, String imageUrl, int maxTokens) {
        return GPTRequestDTO.builder()
                .model(model)
                .messages(List.of(MessageDTO.createTextAndImage(role, text, imageUrl)))
                .maxTokens(maxTokens)
                .build();
    }
}
