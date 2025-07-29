package com.kbulkup.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoProfileResponse {

    @JsonProperty("id")
    private String providerId; // 카카오의 고유 사용자 ID

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    // 내부 클래스로 계정 정보 모델링
    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        // 내부 클래스로 프로필 정보 모델링
        @Getter
        @NoArgsConstructor
        public static class Profile {
            private String nickname;
        }
    }

    // 편의 메소드: 닉네임 추출
    public String getNickname() {
        if (kakaoAccount != null && kakaoAccount.profile != null) {
            return kakaoAccount.profile.nickname;
        }
        return null;
    }

    // 편의 메소드: 이메일 추출
    public String getEmail() {
        if (kakaoAccount != null) {
            return kakaoAccount.email;
        }
        return null;
    }
}