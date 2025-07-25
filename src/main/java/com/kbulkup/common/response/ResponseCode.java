package com.kbulkup.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //400 BadRequest
    VALIDATION_ERROR(BAD_REQUEST, "사용자 입력값이 올바르지 않습니다."),
    INVALID_LOGIN_REQUEST(BAD_REQUEST, "가입되지 않은 이메일이거나 소셜 로그인 계정입니다."),
    INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_ROLE(BAD_REQUEST, "요청한 역할로 로그인할 수 없습니다. 사용자의 역할 목록에 없습니다."),
    NO_ROLE_ASSIGNED(BAD_REQUEST, "사용자에게 할당된 역할이 없습니다."),
    DUPLICATE_ROLE(BAD_REQUEST, "이미 해당 역할로 가입된 사용자입니다."),
    UNSUPPORTED_LOGIN_TYPE(BAD_REQUEST, "지원하지 않는 로그인 타입입니다."),

    //200 OK
    SUCCESS(OK,"성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
