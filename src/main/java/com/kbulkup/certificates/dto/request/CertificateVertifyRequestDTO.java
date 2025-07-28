package com.kbulkup.certificates.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateVertifyRequestDTO {

    private String certType; //자격증 종류
    private String successDocNo; //합격증 중앙에 위치한 번호
    private String birth; //생년월일
    private String successCtfyNoLic; //발급번호 마지막 6자리

}
