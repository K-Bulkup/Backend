package com.kbulkup.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel(description = "회원가입 요청")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @ApiModelProperty(value = "사용자 ID(수정용)")
    private Long userId;

    @ApiModelProperty(value = "비밀번호(8~64자, 소문자/숫자/특수문자 포함)")
    @Size(min = 8, max = 64)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$")
    private String password;

    @ApiModelProperty(value = "사용자 이름", required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "전화번호")
    private String phone;

    @ApiModelProperty(value = "주소")
    private String address;

    @ApiModelProperty(value = "역할", required = true, allowableValues = "TRAINEE,TRAINER")
    @NotNull
    private String role;

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER")
    private String loginType;

    @ApiModelProperty(value = "소셜 제공자 ID")
    private String providerId;

    @ApiModelProperty(value = "생년월일(yyyyMMddHHmmss)")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime birthdate;
}
