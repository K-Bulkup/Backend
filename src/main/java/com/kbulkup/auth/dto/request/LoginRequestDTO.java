package com.kbulkup.auth.dto.request;

import lombok.*;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "로그인 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "비밀번호(로컬 로그인 시)", example = "P@ssw0rd!")
    private String password;

    @ApiModelProperty(value = "소셜 인증 코드(소셜 로그인 시)", example = "AUTH_CODE_FROM_PROVIDER")
    private String code;

    @ApiModelProperty(value = "요청 역할", example = "TRAINEE")
    private String role;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER", example = "LOCAL")
    private String loginType;
}
