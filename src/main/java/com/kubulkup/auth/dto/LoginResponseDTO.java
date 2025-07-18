package com.kubulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String message;     // 예: "로그인 성공"
    private String accessToken; // 최종 인증 JWT
    private Long userId;        // 사용자의 고유 ID
    private String nickname;    // 사용자의 닉네임
    private List<String> roles; // 사용자가 가진 모든 역할 목록
}
