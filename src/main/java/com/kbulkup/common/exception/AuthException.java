package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class AuthException extends BaseException {
    public AuthException(ResponseCode responseCode) {
        super(responseCode);
    }
}
