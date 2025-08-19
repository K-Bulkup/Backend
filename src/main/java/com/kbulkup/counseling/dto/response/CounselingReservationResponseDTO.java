package com.kbulkup.counseling.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "상담 예약 응답")
@Getter
public class CounselingReservationResponseDTO {

    @ApiModelProperty(value = "예약 ID", example = "9001")
    private Long reservationId;

    @ApiModelProperty(value = "수강생 ID", example = "1001")
    private Long traineeId;

    @ApiModelProperty(value = "트레이너 ID", example = "3001")
    private Long trainerId;

    @ApiModelProperty(value = "트레이닝 ID", example = "2001")
    private Long trainingId;

    @ApiModelProperty(value = "스케줄 ID", example = "7001")
    private Long scheduleId;

    @ApiModelProperty(value = "상태", example = "RESERVED")
    private String status;

    @ApiModelProperty(value = "상담방 ID", example = "ROOM-abc123")
    private String roomId;

    @ApiModelProperty(value = "시작 시각", example = "2025-08-18T10:00:00")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "종료 시각", example = "2025-08-18T10:30:00")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "트레이너 이름", example = "trainer_lee")
    private String trainerName;

    @ApiModelProperty(value = "수강생 이름", example = "trainee_kim")
    private String traineeName;

    @ApiModelProperty(value = "트레이닝 제목", example = "아침 스트레칭 루틴")
    private String trainingTitle;

    @ApiModelProperty(value = "생성 시각", example = "2025-08-17T12:00:00")
    private LocalDateTime createdAt;
}
