package com.kbulkup.common.exception;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CustomResponse<Void>> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(e.getResponseCode().getHttpStatus())
                .body(CustomResponse.error(e.getResponseCode()));
    }

    //@Valid 어노테이션 validation 실패 시 발생하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(ResponseCode.VALIDATION_ERROR.getHttpStatus())
                .body(CustomResponse.error(ResponseCode.VALIDATION_ERROR));
    }

}
