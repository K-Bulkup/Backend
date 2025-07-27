package com.kbulkup.certificates.controller;

import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.service.CertificatesService;
import com.kbulkup.common.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @PostMapping("/verification/{trainerId}")
    private CustomResponse<Void> verifyCertification(@PathVariable Long trainerId,
                                                     @RequestBody CertificateVertifyRequestDTO dto) {

        return certificatesService.createTrainerCertification(trainerId,dto);

    }

}
