package com.kbulkup.counseling.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "상담 예약 생성 요청")
@Getter
public class ReservationCreateRequestDTO {

    @ApiModelProperty(value = "트레이너 ID", required = true, example = "3001")
    private Long trainerId;

    @ApiModelProperty(value = "트레이닝 ID", required = true, example = "2001")
    private Long trainingId;

    @ApiModelProperty(value = "시작 시각(ISO-8601)", required = true, example = "2025-08-18T10:00:00")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "종료 시각(ISO-8601)", required = true, example = "2025-08-18T10:30:00")
    private LocalDateTime endTime;
}
