package com.kbulkup.certificates.service;

import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.common.response.CustomResponse;

public interface CertificatesService {

    CustomResponse<Void> createTrainerCertification(Long trainerId, CertificateVertifyRequestDTO dto);

}
