package com.kbulkup.admin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "가입자 추이 통계 항목")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserStatisticsResponseDTO {

    @ApiModelProperty(value = "라벨(날짜/주/월 표시)", example = "2024-08-01")
    private String dateLabel;

    @ApiModelProperty(value = "가입자 수", example = "23")
    private long signupCount;
}
