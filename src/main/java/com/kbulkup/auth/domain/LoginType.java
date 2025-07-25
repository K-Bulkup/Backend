package com.kbulkup.auth.domain;

import com.kbulkup.auth.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    LOCAL("LOCAL", "일반 로그인"),
    KAKAO("KAKAO", "카카오 로그인"),
    NAVER("NAVER", "네이버 로그인");

    private final String key;
    private final String description;

    public static LoginType fromKey(String key) {
        return Arrays.stream(LoginType.values())
                .filter(type -> type.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new AuthException(ResponseCode.UNSUPPORTED_LOGIN_TYPE));
    }
}
