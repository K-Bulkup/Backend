package com.kbulkup.routine.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "수강생 루틴 상세 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRoutineDetailResponseDTO {

    @ApiModelProperty(value = "루틴 제목")
    private String routineTitle;

    @ApiModelProperty(value = "루틴 설명")
    private String routineDescription;

    @ApiModelProperty(value = "루틴 점수")
    private int routineScore;

    @ApiModelProperty(value = "트레이닝 카테고리")
    private String category;

    @ApiModelProperty(value = "트레이닝 난이도")
    private String level;

    @ApiModelProperty(value = "루틴 영상 URL")
    private String routineVideoUrl;

    @ApiModelProperty(value = "루틴 타입")
    private String routineType;

    @ApiModelProperty(value = "퀴즈 타입")
    private String quizType;
}
