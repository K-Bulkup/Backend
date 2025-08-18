package com.kbulkup.routine.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "수강생 루틴 답변 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDTO {

    @ApiModelProperty(value = "사용자 제출 답변")
    private String answer;

    @ApiModelProperty(value = "제출 답변 텍스트 여부")
    private boolean isText;
}
