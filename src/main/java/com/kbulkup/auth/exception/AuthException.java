package com.kbulkup.auth.exception;

import com.kbulkup.common.exception.BaseException;
import com.kbulkup.common.response.ResponseCode;

public class AuthException extends BaseException {
    public AuthException(ResponseCode responseCode) {
        super(responseCode);
    }
}
