package com.kbulkup.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // 400 BadRequest
    AUTH_VALIDATION_ERROR(BAD_REQUEST, "사용자 입력값이 올바르지 않습니다."),
    AUTH_INVALID_PASSWORD(BAD_REQUEST, "잘못된 비밀번호입니다."),
    AUTH_INVALID_ROLE(BAD_REQUEST, "잘못된 역할입니다."),
    AUTH_INVALID_LOGIN_TYPE(BAD_REQUEST, "잘못된 로그인 형식입니다."),
    AUTH_EMAIL_NOT_FOUND(BAD_REQUEST, "존재하지 않는 이메일입니다."),
    AUTH_INVALID_TOKEN(BAD_REQUEST, "토큰이 올바르지 않습니다."),
    AUTH_USER_NOT_FOUND(BAD_REQUEST, "없는 사용자 입니다"),
    AUTH_UNSUPPORTED_LOGIN_TYPE(BAD_REQUEST, "지원하지 않는 로그인 타입입니다."),
    AUTH_NAVER_TOKEN_FAILURE(BAD_REQUEST, "네이버 액세스 토큰 발급에 실패했습니다."),
    AUTH_NOT_FOUND_TRAINER_PROFILE(BAD_REQUEST, "트레이너 프로필 정보를 찾을 수 없습니다."),
    AUTH_JWT_PARSING_FAILED(BAD_REQUEST, "JWT 파싱 중 오류 발생"),
    AUTH_JWT_EXPIRED(BAD_REQUEST, "JWT 토큰이 만료되었습니다."),
    AUTH_JWT_UNSUPPORTED(BAD_REQUEST, "지원하지 않는 JWT 형식입니다."),
    AUTH_JWT_MALFORMED(BAD_REQUEST, "잘못된 JWT 형식입니다."),
    AI_CHAT_LIMIT_EXCEEDED(BAD_REQUEST, "하루 AI 채팅 횟수를 초과했습니다. 내일 다시 시도해주세요."),

    VALIDATION_ERROR(BAD_REQUEST, "요청값의 형식이 올바르지 않습니다."),
    INVALID_LOGIN_REQUEST(BAD_REQUEST, "가입되지 않은 이메일이거나 소셜 로그인 계정입니다."),
    NO_ROLE_ASSIGNED(BAD_REQUEST, "사용자에게 할당된 역할이 없습니다."),
    DUPLICATE_ROLE(BAD_REQUEST, "이미 해당 역할로 가입된 사용자입니다."),
    NOT_FOUND_TRAINER_PROFILE(BAD_REQUEST, "트레이너 프로필 정보를 찾을 수 없습니다."), // 메시지 중복 있어서 위에 하나 있음
    NOT_FOUND_TRAINEE_PROFILE(BAD_REQUEST, "트레이니 프로필 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(BAD_REQUEST, "사용자를 찾을 수 없습니다."), // AUTH_USER_NOT_FOUND 와 의미 중복
    INVALID_ENUM(BAD_REQUEST, "유효하지 않은 ENUM 값입니다."),
    INVALID_SQL_QUERY(BAD_REQUEST, "올바르지 않은 SQL 쿼리문입니다."),
    PAYMENT_VERIFICATION_FAILED(BAD_REQUEST, "결제 검증에 실패했습니다."),
    TRAINING_NOT_FOUND(BAD_REQUEST, "존재하지 않는 트레이닝이거나 수강권한이 없습니다."),

    ADMIN_INVALID_DATE_VALUE(BAD_REQUEST,"유효하지 않은 날짜입니다." ),


    // 401 Unauthorized
    AUTH_JWT_INVALID_SIGNATURE(UNAUTHORIZED, "JWT 서명이 유효하지 않습니다."),
    AUTH_TOKEN_INVALID_OR_EXPIRED(UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."),

    // 403 FORBIDDEN
    ADMIN_USER_ROLE_MISSING(FORBIDDEN,"잘못된 역할입니다." ),

    //500 InternalServerError
    TRAINER_CAREER_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 소개 업데이트에 실패했습니다."),
    TRAINER_PROFILE_NULL_VALUE_ERROR(INTERNAL_SERVER_ERROR, "프로필 이미지가 전달되지 않았습니다."),
    TRAINER_PROFILE_IMAGE_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 프로필 이미지 업데이트에 실패했습니다."),
    TRAINER_CERTIFICATES_VALIDATION_FAILED(INTERNAL_SERVER_ERROR, "유효하지 않은 자격정보 입니다."),
    PAYMENT_PROCESS_FAILED(INTERNAL_SERVER_ERROR, "결제 처리 중 오류가 발생했습니다."),
    PAYMENT_SAVE_FAILED(INTERNAL_SERVER_ERROR, "결제 정보 저장 중 오류가 발생했습니다."),



    //200 OK
    SUCCESS(OK, "성공했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

}
