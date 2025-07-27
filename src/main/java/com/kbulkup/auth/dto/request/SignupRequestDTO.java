package com.kbulkup.auth.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequestDTO {
    private String userId;
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$",
            message = "비밀번호는 최소 8자, 최대 64자이며, 소문자, 숫자, 특수문자(@$!%*?&)를 각각 1개 이상 포함해야 합니다."
    )
    private String password;

    private String name;
    private String email;
    private String phone;
    private String address;
    @NotNull(message = "역할은 필수 선택 사항입니다.")
    private String role; // 추가: "TRAINEE", "TRAINER" 등
    private String loginType; // 추가: "LOCAL", "KAKAO", "NAVER" 등
    private String providerId; // 소셜 로그인 제공자 ID
    private String birthdate;
}