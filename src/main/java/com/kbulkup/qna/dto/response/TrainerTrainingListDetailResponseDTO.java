package com.kbulkup.qna.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "트레이너 담당 트레이닝 QnA 개요 항목")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingListDetailResponseDTO {

    @ApiModelProperty(value = "트레이닝 ID")
    private Long trainingId;

    @ApiModelProperty(value = "트레이닝 제목")
    private String trainingTitle;
}
