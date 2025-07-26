package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class ProfileException extends BaseException {
    public ProfileException(ResponseCode responseCode) {
        super(responseCode);
    }
}
