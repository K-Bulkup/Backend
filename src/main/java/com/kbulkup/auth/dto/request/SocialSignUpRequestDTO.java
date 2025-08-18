package com.kbulkup.auth.dto.request;

import lombok.*;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "소셜 회원가입 완료 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialSignUpRequestDTO {

    /**
     * 소셜 로그인 1단계 완료 후 발급받은 임시 토큰입니다.
     * 이 토큰으로 사용자를 식별하고 권한을 검증합니다.
     */
    @ApiModelProperty(value = "임시 액세스 토큰", required = true, example = "temp_token_abcdef")
    private String tempAccessToken;

    /**
     * 사용자가 선택한 최종 역할입니다.
     * "TRAINER" 또는 "TRAINEE"와 같은 문자열이 담깁니다.
     */
    @ApiModelProperty(value = "역할", required = true, allowableValues = "TRAINER,TRAINEE", example = "TRAINEE")
    private String role;
}
