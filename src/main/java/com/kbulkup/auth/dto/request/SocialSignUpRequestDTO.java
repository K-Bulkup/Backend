package com.kbulkup.auth.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialSignUpRequestDTO {

    /**
     * 소셜 로그인 1단계 완료 후 발급받은 임시 토큰입니다.
     * 이 토큰으로 사용자를 식별하고 권한을 검증합니다.
     */
    private String tempAccessToken;

    /**
     * 사용자가 선택한 최종 역할입니다.
     * "TRAINER" 또는 "TRAINEE"와 같은 문자열이 담깁니다.
     */
    private String role;
}