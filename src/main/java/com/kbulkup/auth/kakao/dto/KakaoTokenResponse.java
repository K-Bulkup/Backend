package com.kbulkup.auth.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // 카카오 API 응답은 snake_case를 사용
public class KakaoTokenResponse {

    private String accessToken;
    private String tokenType; // 예: bearer
    private String refreshToken;
    private Integer expiresIn; // 액세스 토큰 만료 시간 (초)
    private String scope;
    private Integer refreshTokenExpiresIn; // 리프레시 토큰 만료 시간 (초)
}