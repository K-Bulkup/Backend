package com.kbulkup.auth.domain;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;

public final class Role {

    public static final String TRAINER = "TRAINER";

    public static final String TRAINEE = "TRAINEE";

    public static final String USER = "ADMIN";

    // 인스턴스 생성을 막기 위한 private 생성자
    private Role() {
        throw new AuthException(ResponseCode.AUTH_INVALID_ROLE);
    }
}