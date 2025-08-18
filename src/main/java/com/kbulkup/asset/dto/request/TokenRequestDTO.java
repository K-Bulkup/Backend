package com.kbulkup.asset.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "자산 연동 은행 요청")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDTO {
    @ApiModelProperty(value = "은행 코드/식별자")
    private String bank;

    public static TokenRequestDTO create(String bank) {
        return TokenRequestDTO.builder().bank(bank).build();
    }
}
