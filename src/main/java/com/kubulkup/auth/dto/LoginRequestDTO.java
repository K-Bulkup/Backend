package com.kubulkup.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {
    private String loginType; // 필수: LOCAL, KAKAO, NAVER
    private String email;        // LOCAL 시 필수
    private String password;     // LOCAL 시 필수
    private String code;         // KAKAO/NAVER 시 필수 (소셜 Authorization Code)
    private String role;       // 필수: 로그인할 역할 (DB의 user_roles 테이블에 이미 존재해야 함)
}