package com.kbulkup.certificates.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "자격증 검증 요청 바디 (민감 정보 포함)")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateVertifyRequestDTO {

    @ApiModelProperty(value = "자격증 종류", required = true, allowableValues = "CPT,PT,NCS,ETC")
    private String certType;

    @ApiModelProperty(value = "합격증 중앙 번호(자격번호)", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String successDocNo;

    @ApiModelProperty(value = "생년월일(yyyyMMdd)", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String birth;

    @ApiModelProperty(value = "발급번호 마지막 6자리", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String successCtfyNoLic;
}
