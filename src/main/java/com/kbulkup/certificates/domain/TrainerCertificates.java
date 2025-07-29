package com.kbulkup.certificates.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerCertificates {

    private Long trainerCertificateId;
    private Long trainerId;
    private CertType certType;
    private String certNumber;
    private LocalDateTime createAt;

}
