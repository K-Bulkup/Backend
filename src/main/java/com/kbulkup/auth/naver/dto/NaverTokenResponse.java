package com.kbulkup.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverTokenResponse {

    /**
     * API 호출로 발급받은 액세스 토큰입니다.
     * 이 토큰으로 사용자 정보를 조회할 수 있습니다.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 액세스 토큰을 갱신하는 데 사용하는 리프레시 토큰입니다.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 토큰의 유형(예: "Bearer")을 나타냅니다.
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 액세스 토큰의 유효 기간(초)입니다.
     */
    @JsonProperty("expires_in")
    private String expiresIn;
}