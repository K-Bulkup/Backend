package com.kbulkup.training.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "트레이닝 검색 파라미터")
@Getter
@Setter
public class TrainingSearchListRequestDTO {
    @ApiModelProperty(value = "검색 키워드")
    private String keyword;
    @ApiModelProperty(value = "트레이너 ID(내 목록 조회시 내부 세팅)")
    private Long trainerId;
}
