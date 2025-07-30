package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class EnumException extends BaseException {
    public EnumException(ResponseCode responseCode) {
        super(responseCode);
    }
}
