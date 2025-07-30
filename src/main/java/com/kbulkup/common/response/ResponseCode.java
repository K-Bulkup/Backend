package com.kbulkup.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //400 BadRequest
    AUTH_VALIDATION_ERROR(BAD_REQUEST, "사용자 입력값이 올바르지 않습니다."),
    AUTH_INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    AUTH_INVALID_ROLE(BAD_REQUEST, "잘못된 역할입니다."),
    AUTH_INVALID_LOGIN_TYPE(BAD_REQUEST,"잘못된 로그인 형식입니다."),
    AUTH_EMAIL_NOT_FOUND(BAD_REQUEST,"존재하지 않는 이메일입니다."),
    AUTH_USER_NOT_FOUND(BAD_REQUEST,"없는 사용자 입니다"),
    AUTH_UNSUPPORTED_LOGIN_TYPE(BAD_REQUEST, "지원하지 않는 로그인 타입입니다."),
    AUTH_NOT_FOUND_TRAINER_PROFILE(BAD_REQUEST,"트레이너 프로필 정보를 찾을 수 없습니다."),
    VALIDATION_ERROR(BAD_REQUEST, "요청값의 형식이 올바르지 않습니다."),


    //500 InternalServerError
    TRAINER_CAREER_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 소개 업데이트에 실패했습니다."),
    TRAINER_PROFILE_IMAGE_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 프로필 이미지 업데이트에 실패했습니다."),
    TRAINER_CERTIFICATES_VALIDATION_FAILED(INTERNAL_SERVER_ERROR, "유효하지 않은 자격정보 입니다."),
    //200 OK
    SUCCESS(OK,"성공했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

}
