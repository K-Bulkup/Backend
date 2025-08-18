package com.kbulkup.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

// Swagger
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "회원가입 요청")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @ApiModelProperty(value = "사용자 ID(수정용)", example = "1001")
    private Long userId;

    @ApiModelProperty(value = "비밀번호(8~64자, 소문자/숫자/특수문자 각 1개 이상)", example = "P@ssw0rd!")
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$",
            message = "비밀번호는 최소 8자, 최대 64자이며, 소문자, 숫자, 특수문자(@$!%*?&)를 각각 1개 이상 포함해야 합니다."
    )
    private String password;

    @ApiModelProperty(value = "사용자 이름", required = true, example = "홍길동")
    @NotNull(message = "사용자 이름은 필수 입력 사항입니다.")
    private String username;

    @ApiModelProperty(value = "이메일", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "전화번호", example = "01012345678")
    private String phone;

    @ApiModelProperty(value = "주소", example = "서울시 강남구 테헤란로 123")
    private String address;

    @ApiModelProperty(value = "역할", required = true, allowableValues = "TRAINEE,TRAINER", example = "TRAINEE")
    @NotNull(message = "역할은 필수 선택 사항입니다.")
    private String role; // 추가: "TRAINEE", "TRAINER" 등

    @ApiModelProperty(value = "로그인 타입", allowableValues = "LOCAL,KAKAO,NAVER", example = "LOCAL")
    private String loginType; // 추가: "LOCAL", "KAKAO", "NAVER" 등

    @ApiModelProperty(value = "소셜 제공자 ID", example = "1234567890")
    private String providerId; // 소셜 로그인 제공자 ID

    @ApiModelProperty(value = "생년월일(yyyyMMddHHmmss)", example = "19900101120000")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime birthdate;
}
