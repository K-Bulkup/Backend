package com.kbulkup.routine.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "루틴 결과 생성 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResultCreateRequestDTO {

    @ApiModelProperty(value = "수강 등록 ID", required = true)
    private Long enrollmentId;

    @ApiModelProperty(value = "텍스트 답변", required = true)
    private String answerText;
}
