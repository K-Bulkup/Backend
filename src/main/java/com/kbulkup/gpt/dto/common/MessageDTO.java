package com.kbulkup.gpt.dto.common;

import com.kbulkup.gpt.dto.request.ImageContentDTO;
import com.kbulkup.gpt.dto.request.TextContentDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String role; //user, assistant, system
    private Object content;

    // ✅ 텍스트만 담긴 메시지 생성
    public static MessageDTO createOnlyText(String role, String text) {
        return MessageDTO.builder()
                .role(role)
                .content(text)
                .build();
    }

    // ✅ 텍스트 + 이미지 메시지 생성
    public static MessageDTO createTextAndImage(String role, String text, String imageUrl) {
        return MessageDTO.builder()
                .role(role)
                .content(List.of(
                        new ImageContentDTO(imageUrl),
                        new TextContentDTO(text)
                ))
                .build();
    }
}
