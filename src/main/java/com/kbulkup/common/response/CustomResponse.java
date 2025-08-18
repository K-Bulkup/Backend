package com.kbulkup.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ApiModel(description = "공통 응답 래퍼")
@Getter
@RequiredArgsConstructor
public class CustomResponse<T> {

    @ApiModelProperty(value = "HTTP 상태코드", example = "200")
    private final int status;

    @ApiModelProperty(value = "성공 여부", example = "true")
    private final boolean isSuccess;

    @ApiModelProperty(value = "응답 코드명(ResponseCode.name())", example = "SUCCESS")
    private final String name;

    @ApiModelProperty(value = "메시지", example = "성공했습니다.")
    private final String message;

    @ApiModelProperty(value = "실제 데이터 페이로드")
    private final T data;

    // factory methods 그대로 유지
    public static <T> CustomResponse<T> success(ResponseCode code, T data) {
        return new CustomResponse<>(code.getHttpStatus().value(), true, code.name(), code.getMessage(), data);
    }
    public static <T> CustomResponse<T> success(ResponseCode code) {
        return new CustomResponse<>(code.getHttpStatus().value(), true, code.name(), code.getMessage(), null);
    }
    public static <T> CustomResponse<T> error(ResponseCode code, T data) {
        return new CustomResponse<>(code.getHttpStatus().value(), false, code.name(), code.getMessage(), data);
    }
    public static <T> CustomResponse<T> error(ResponseCode code) {
        return new CustomResponse<>(code.getHttpStatus().value(), false, code.name(), code.getMessage(), null);
    }
}
