package com.kbulkup.gpt.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@ApiModel(description = "GPT 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GPTResponseDTO {

    @ApiModelProperty(value = "응답 ID")
    private String id;

    @ApiModelProperty(value = "남은 AI 상담 가능 횟수")
    @Setter
    private int remainingChats;

    @ApiModelProperty(value = "응답 선택지 목록")
    private List<ChoiceDTO> choices;
}
