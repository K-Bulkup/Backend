package com.kbulkup.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON에서 제외
public class SocialLoginResponseDTO {

    private LoginStatus status; // "LOGIN_SUCCESS" 또는 "SIGNUP_REQUIRED"

    // 로그인 성공 시 채워지는 필드
    private String accessToken;
    private Long userId;
    private String nickname;
    private List<String> roles;

    // 회원가입 필요 시 채워지는 필드
    private String preAuthToken;

    public enum LoginStatus {
        LOGIN_SUCCESS, SIGNUP_REQUIRED
    }
}
