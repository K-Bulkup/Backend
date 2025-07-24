package com.kbulkup.common.exception;

import com.kbulkup.common.response.CustomResponse;
import org.springframework.http.ResponseEntity;
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

}
