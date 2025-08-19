package com.kbulkup.qna.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 질문 작성 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingQnARequestDTO {

    @ApiModelProperty(value = "질문 제목", required = true)
    private String questionTitle;

    @ApiModelProperty(value = "질문 본문", required = true)
    private String question;
}
