package com.kbulkup.gpt.dto.common;

import com.kbulkup.gpt.dto.request.ImageContentDTO;
import com.kbulkup.gpt.dto.request.TextContentDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "메시지 모델(역할/내용)")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    @ApiModelProperty(value = "역할", allowableValues = "user,assistant,system", example = "assistant")
    private String role; // user, assistant, system

    @ApiModelProperty(
            value = "내용(텍스트 문자열 또는 복합 콘텐츠)",
            notes = "텍스트 응답의 경우 문자열. 복합 응답의 경우 [image_url, text] 형태의 배열일 수 있습니다."
    )
    private Object content;

    // 텍스트만 담긴 메시지 생성
    public static MessageDTO createOnlyText(String role, String text) {
        return MessageDTO.builder()
                .role(role)
                .content(text)
                .build();
    }

    // 텍스트 + 이미지 메시지 생성
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
