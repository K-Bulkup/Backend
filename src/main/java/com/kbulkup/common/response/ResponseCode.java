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
    NOT_FOUND_TRAINER_PROFILE(BAD_REQUEST,"트레이너 프로필 정보를 찾을 수 없습니다."),

    //500 InternalServerError
    TRAINER_CAREER_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 소개 업데이트에 실패했습니다."),
    TRAINER_PROFILE_IMAGE_UPDATE_FAILED(INTERNAL_SERVER_ERROR, "트레이너 프로필 이미지 업데이트에 실패했습니다."),
    TRAINER_CERTIFICATES_VALIDATION_FAILED(INTERNAL_SERVER_ERROR, "유효하지 않은 자격정보 입니다."),
    //200 OK
    SUCCESS(OK,"성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
