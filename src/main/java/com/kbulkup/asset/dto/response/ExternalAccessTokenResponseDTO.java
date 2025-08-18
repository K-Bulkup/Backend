package com.kbulkup.asset.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "외부 액세스 토큰 응답")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAccessTokenResponseDTO {

    @ApiModelProperty(value = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String accessToken;
}
