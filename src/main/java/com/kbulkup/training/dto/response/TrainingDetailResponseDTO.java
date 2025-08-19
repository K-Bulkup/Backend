package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "트레이닝 상세(내부 공용 DTO)")
@Getter
@NoArgsConstructor
public class TrainingDetailResponseDTO {
    @ApiModelProperty("제목") private String title;
    @ApiModelProperty("설명") private String description;
    @ApiModelProperty("가격") private int price;
    @ApiModelProperty("카테고리") private String category;
    @ApiModelProperty("난이도") private String level;
    @ApiModelProperty("루틴 총점수") private int totalScore;
    @ApiModelProperty("평균 평점") private float averageRating;
    @ApiModelProperty("수강생 수") private float traineeCount;
    @ApiModelProperty("진행률(0~100)") private float progress;
    @ApiModelProperty("루틴 목록") private List<RoutineResponseDTO> routines;

    @ApiModel(description = "루틴 항목")
    @Getter
    @NoArgsConstructor
    public static class RoutineResponseDTO {
        @ApiModelProperty("루틴 ID") private Long routineId;
        @ApiModelProperty("제목") private String title;
        @ApiModelProperty("완료 여부") private boolean completed;
        @ApiModelProperty("보상 포인트") private int rewardPoint;
        @ApiModelProperty("완료 시각") private LocalDateTime completedAt;
    }
}
