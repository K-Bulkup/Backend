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

    //200 OK
    SUCCESS(OK,"성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
