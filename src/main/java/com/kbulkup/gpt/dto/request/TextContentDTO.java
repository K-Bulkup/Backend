package com.kbulkup.gpt.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class TextContentDTO extends ContentDTO {
    private String text;

    public TextContentDTO(String text) {
        super("text");
        this.text = text;
    }
}
