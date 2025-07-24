package com.kbulkup.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CustomResponse<T> {

    private final int status;
    private final boolean isSuccess;
    private final String name;
    private final String message;
    private final T data;

    //success 관련 (데이터 포함)
    public static<T> CustomResponse<T> success(ResponseCode code, T data) {
        return new CustomResponse<>(code.getHttpStatus().value(),true,code.name(),code.getMessage(),data);
    }

    //success 관련 (데이터 미포함)
    public static<T> CustomResponse<T> success(ResponseCode code) {
        return new CustomResponse<>(code.getHttpStatus().value(),true,code.name(),code.getMessage(),null);
    }

    //error 관련 (데이터 포함)
    public static<T> CustomResponse<T> error(ResponseCode code, T data) {
        return new CustomResponse<>(code.getHttpStatus().value(),false,code.name(),code.getMessage(), data);
    }

    //error 관련 (데이터 미포함)
    public static<T> CustomResponse<T> error(ResponseCode code) {
        return new CustomResponse<>(code.getHttpStatus().value(),false,code.name(),code.getMessage(),null);
    }

}
