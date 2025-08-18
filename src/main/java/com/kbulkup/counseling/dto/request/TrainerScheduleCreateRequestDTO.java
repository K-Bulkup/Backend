package com.kbulkup.counseling.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "트레이너 스케줄 등록 요청")
@Getter
public class TrainerScheduleCreateRequestDTO {

    @ApiModelProperty(value = "타임슬롯 배열", required = true)
    private List<TimeSlotDTO> timeSlots;

    @ApiModel(description = "30분 단위 타임슬롯")
    @Getter
    public static class TimeSlotDTO {
        @ApiModelProperty(value = "시작 시각(ISO-8601)", required = true, example = "2025-08-18T09:00:00")
        private LocalDateTime startTime;

        @ApiModelProperty(value = "종료 시각(ISO-8601)", required = true, example = "2025-08-18T09:30:00")
        private LocalDateTime endTime;
    }
}
