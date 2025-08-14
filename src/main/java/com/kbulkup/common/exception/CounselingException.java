package com.kbulkup.common.exception;

import com.kbulkup.common.response.ResponseCode;

public class CounselingException extends BaseException {
  public CounselingException(ResponseCode responseCode) {
    super(responseCode);
  }
}
