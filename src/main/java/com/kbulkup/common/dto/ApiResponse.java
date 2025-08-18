package com.kbulkup.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 내부용: Swagger 문서에서 제외
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
}
