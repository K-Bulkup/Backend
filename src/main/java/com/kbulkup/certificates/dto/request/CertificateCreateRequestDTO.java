package com.kbulkup.certificates.dto.request;

import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateCreateRequestDTO {

    private Long trainerId;
    private String certType;
    private String certNumber;

    public static CertificateCreateRequestDTO create(Long trainerId, CertificateVertifyRequestDTO dto) {
        return CertificateCreateRequestDTO.builder()
                .trainerId(trainerId)
                .certType(dto.getCertType())
                .certNumber(dto.getSuccessDocNo())
                .build();
    }
}
