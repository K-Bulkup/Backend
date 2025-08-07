package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class QnAException extends BaseException {
    public QnAException(ResponseCode responseCode) {
        super(responseCode);
    }
}
