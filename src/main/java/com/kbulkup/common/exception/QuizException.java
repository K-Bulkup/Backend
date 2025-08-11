package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class QuizException extends BaseException {
    public QuizException(ResponseCode responseCode) {
        super(responseCode);
    }
}
