package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class UserException extends BaseException{

    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
