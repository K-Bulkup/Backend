package com.kbulkup.asset.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "외부 토큰/핀테크번호 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalTokenResponseDTO {

    @ApiModelProperty(value = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String accessToken;

    @ApiModelProperty(value = "핀테크 이용번호", example = "199999999012345678901234")
    private String fintechUseNum;
}
