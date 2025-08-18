package com.kbulkup.gpt.dto.response;

import com.kbulkup.gpt.dto.common.MessageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "GPT 응답 선택지")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDTO {

    @ApiModelProperty(value = "선택지 인덱스")
    private int index;

    @ApiModelProperty(value = "메시지(역할/내용)")
    private MessageDTO message;
}
