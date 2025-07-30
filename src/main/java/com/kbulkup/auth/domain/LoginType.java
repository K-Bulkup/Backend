package com.kbulkup.auth.domain;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;

public class LoginType {
    public static final String LOCAL = "LOCAL";
    public static final String KAKAO = "KAKAO";
    public static final String NAVER = "NAVER";

    private LoginType() {
        throw new AuthException(ResponseCode.AUTH_INVALID_LOGIN_TYPE);
    }
}
