package com.kbulkup.admin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "관리자용 트레이닝 요약 응답")
@Getter
public class AdminTrainingResponseDTO {

    @ApiModelProperty(value = "트레이닝 ID", example = "101")
    private Long trainingId;

    @ApiModelProperty(value = "트레이닝명", example = "아침 스트레칭 루틴")
    private String trainingName;

    @ApiModelProperty(value = "트레이너 ID", example = "501")
    private Long trainerId;

    @ApiModelProperty(value = "트레이너 이름", example = "trainer_lee")
    private String trainerName; // 추가

    @ApiModelProperty(value = "승인 상태", example = "APPROVED",
            allowableValues = "PENDING,APPROVED,REJECTED")
    private String approvalStatus;

    @ApiModelProperty(value = "총 수강생 수", example = "128")
    private int totalStudents;

    @ApiModelProperty(value = "요청일시(등록일, ISO-8601)", example = "2024-08-01T10:15:30")
    private String requestDate; // 추가 (created_at을 매핑)
}
