package com.kbulkup.counseling.dto.response;

import com.kbulkup.counseling.domain.TrainerSchedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "트레이너 스케줄 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerScheduleResponseDTO {

    @ApiModelProperty(value = "스케줄 ID", example = "7001")
    private Long scheduleId;

    @ApiModelProperty(value = "트레이너 ID", example = "3001")
    private Long trainerId;

    @ApiModelProperty(value = "시작 시각", example = "2025-08-18T09:00:00")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "종료 시각", example = "2025-08-18T09:30:00")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "예약 가능 여부", example = "true")
    private Boolean isAvailable;

    @ApiModelProperty(value = "생성 시각", example = "2025-08-10T09:00:00")
    private LocalDateTime createdAt;

    public static TrainerScheduleResponseDTO createDTO(TrainerSchedule schedule) {
        return TrainerScheduleResponseDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .trainerId(schedule.getTrainerId())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .isAvailable(schedule.getIsAvailable())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
