package com.kbulkup.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "로그인 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "비밀번호(로컬 로그인 시)")
    private String password;

    @ApiModelProperty(value = "소셜 인증 코드(소셜 로그인 시)")
    private String code;

    @ApiModelProperty(value = "요청 역할", allowableValues = "TRAINEE,TRAINER")
    private String role;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER")
    private String loginType;
}
