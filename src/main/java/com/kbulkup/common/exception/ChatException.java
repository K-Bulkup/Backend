package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class ChatException extends BaseException {
    public ChatException(ResponseCode responseCode) {
        super(responseCode);
    }
}
