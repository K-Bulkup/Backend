package com.kbulkup.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConsultRequestDTO {

    @JsonProperty("isAsset")
    private boolean isAsset;

    @JsonProperty("question")
    private String question;
}
