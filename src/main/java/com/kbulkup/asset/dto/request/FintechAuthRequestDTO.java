package com.kbulkup.asset.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "핀테크 인증 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FintechAuthRequestDTO {

    @ApiModelProperty(value = "핀테크 이용번호", example = "199999999012345678901234")
    private String FintechUseNum;
}
