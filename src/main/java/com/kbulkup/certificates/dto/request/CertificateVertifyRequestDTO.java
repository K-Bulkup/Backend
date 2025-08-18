package com.kbulkup.certificates.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "자격증 검증 요청 바디 (민감 정보 포함)")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateVertifyRequestDTO {

    @ApiModelProperty(value = "자격증 종류", example = "CPT") // 예: CPT, PT, NCS 등
    private String certType; // 자격증 종류

    @ApiModelProperty(
            value = "합격증 중앙 번호(자격번호)",
            example = "AB-****-1234",
            notes = "민감 정보: 실제 값은 마스킹하여 전송 권장, 서버 로그에 남기지 마세요."
    )
    private String successDocNo; // 합격증 중앙에 위치한 번호

    @ApiModelProperty(
            value = "생년월일(yyyyMMdd)",
            example = "19900101",
            notes = "민감 정보: 서버 로그/에러 메시지에 노출 금지"
    )
    private String birth; // 생년월일

    @ApiModelProperty(
            value = "발급번호 마지막 6자리",
            example = "123456",
            notes = "민감 정보: 서버 로그/에러 메시지에 노출 금지"
    )
    private String successCtfyNoLic; // 발급번호 마지막 6자리
}
