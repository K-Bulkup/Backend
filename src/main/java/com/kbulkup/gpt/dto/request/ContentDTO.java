package com.kbulkup.gpt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ContentDTO {
    private String type;

    protected ContentDTO(String type) {
        this.type = type;
    }
}
