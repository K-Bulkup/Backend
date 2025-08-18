package com.kbulkup.routine.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "루틴 요약 항목")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSummaryResponseDTO {

    @ApiModelProperty(value = "루틴 ID")
    private Long routineId;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "완료 여부")
    private boolean completed;

    @ApiModelProperty(value = "보상 포인트")
    private int rewardPoint;

    @ApiModelProperty(value = "완료 시각")
    private LocalDateTime completedAt;

    @ApiModelProperty(value = "루틴 타입")
    private String routineType;

    @ApiModelProperty(value = "퀴즈 타입")
    private String quizType;

    public static RoutineSummaryResponseDTO of(
            Long routineId, String title, boolean completed, int rewardPoint, LocalDateTime completedAt, String routineType, String quizType
    ) {
        return RoutineSummaryResponseDTO.builder()
                .routineId(routineId)
                .title(title)
                .completed(completed)
                .rewardPoint(rewardPoint)
                .completedAt(completedAt)
                .routineType(routineType)
                .quizType(quizType)
                .build();
    }
}
