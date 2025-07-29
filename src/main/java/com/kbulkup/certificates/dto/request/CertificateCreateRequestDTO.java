package com.kbulkup.certificates.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateCreateRequestDTO {

    private Long trainerID;
    private String certType;
    private String certNumber;

    public static CertificateCreateRequestDTO create(Long trainerId, CertificateVertifyRequestDTO dto) {
        return CertificateCreateRequestDTO.builder()
                .trainerID(trainerId)
                .certType(dto.getCertType())
                .certNumber(dto.getSuccessDocNo())
                .build();
    }

}
