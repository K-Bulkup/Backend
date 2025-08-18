package com.kbulkup.admin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "관리자용 트레이닝 요약 응답")
@Getter
public class AdminTrainingResponseDTO {

    @ApiModelProperty(value = "트레이닝 ID")
    private Long trainingId;

    @ApiModelProperty(value = "트레이닝명")
    private String trainingName;

    @ApiModelProperty(value = "트레이너 ID")
    private Long trainerId;

    @ApiModelProperty(value = "트레이너 이름")
    private String trainerName;

    @ApiModelProperty(value = "승인 상태", allowableValues = "PENDING,APPROVED,REJECTED")
    private String approvalStatus;

    @ApiModelProperty(value = "총 수강생 수")
    private int totalStudents;

    @ApiModelProperty(value = "요청일시(등록일, ISO-8601)")
    private String requestDate;
}
