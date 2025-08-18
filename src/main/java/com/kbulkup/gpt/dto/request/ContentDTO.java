package com.kbulkup.gpt.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 내부용
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
