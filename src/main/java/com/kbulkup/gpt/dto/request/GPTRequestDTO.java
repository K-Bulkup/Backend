package com.kbulkup.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbulkup.gpt.dto.common.MessageDTO;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore // 내부용: Swagger에 노출하지 않음
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

    public static GPTRequestDTO createOnlyText(String model, String role, String text, int maxTokens) {
        return GPTRequestDTO.builder()
                .model(model)
                .messages(List.of(MessageDTO.createOnlyText(role, text)))
                .maxTokens(maxTokens)
                .build();
    }

    public static GPTRequestDTO createWithTextAndImage(String model, String role, String text, String imageUrl, int maxTokens) {
        return GPTRequestDTO.builder()
                .model(model)
                .messages(List.of(MessageDTO.createTextAndImage(role, text, imageUrl)))
                .maxTokens(maxTokens)
                .build();
    }
}
