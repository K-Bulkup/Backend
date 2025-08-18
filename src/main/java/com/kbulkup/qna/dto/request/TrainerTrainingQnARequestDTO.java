package com.kbulkup.qna.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이닝 답변 작성 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingQnARequestDTO {

    @ApiModelProperty(value = "QnA ID", required = true)
    private Long qnaId;

    @ApiModelProperty(value = "답변 본문", required = true)
    private String answer;
}
