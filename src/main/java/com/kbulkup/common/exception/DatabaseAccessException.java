package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class DatabaseAccessException extends BaseException {
    public DatabaseAccessException(ResponseCode responseCode) {
        super(responseCode);
    }
}
