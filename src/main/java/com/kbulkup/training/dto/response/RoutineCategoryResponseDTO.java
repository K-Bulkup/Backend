package com.kbulkup.training.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "루틴 카테고리 응답")
@Getter
@NoArgsConstructor
public class RoutineCategoryResponseDTO {
    @ApiModelProperty("카테고리")
    private String category;
    @ApiModelProperty("루틴 제목")
    private String routineTitle;
    @ApiModelProperty("퀴즈 타입")
    private String quizType;
}
