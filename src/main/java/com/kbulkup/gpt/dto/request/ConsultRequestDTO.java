package com.kbulkup.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "GPT 상담 요청 바디")
@Getter
public class ConsultRequestDTO {

    @ApiModelProperty(value = "자산 상담 여부", required = true, example = "true")
    @JsonProperty("isAsset")
    private boolean isAsset;

    @ApiModelProperty(value = "상담 질문", required = true)
    @JsonProperty("question")
    private String question;
}
