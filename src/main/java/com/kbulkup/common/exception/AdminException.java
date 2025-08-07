package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class AdminException extends BaseException {
  public AdminException(ResponseCode responseCode) {
    super(responseCode);
  }
}
