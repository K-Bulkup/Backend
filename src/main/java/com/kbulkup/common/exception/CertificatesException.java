package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class CertificatesException extends BaseException {
    public CertificatesException(ResponseCode responseCode) {
        super(responseCode);
    }
}
