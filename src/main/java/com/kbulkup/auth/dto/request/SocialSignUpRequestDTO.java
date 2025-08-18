package com.kbulkup.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "소셜 회원가입 완료 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialSignUpRequestDTO {

    @ApiModelProperty(value = "임시 액세스 토큰", required = true)
    private String tempAccessToken;

    @ApiModelProperty(value = "역할", required = true, allowableValues = "TRAINER,TRAINEE")
    private String role;
}
