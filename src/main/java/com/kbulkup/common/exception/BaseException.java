package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final ResponseCode responseCode;

}
