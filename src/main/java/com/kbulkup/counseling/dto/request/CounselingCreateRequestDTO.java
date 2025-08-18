package com.kbulkup.counseling.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "상담방 생성 요청")
@Getter
public class CounselingCreateRequestDTO {

    @ApiModelProperty(value = "수강생 ID", required = true, example = "1001")
    private Long traineeId;

    @ApiModelProperty(value = "트레이닝 ID", required = true, example = "2001")
    private Long trainingId;
}
