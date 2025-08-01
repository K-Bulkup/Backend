package com.kbulkup.certificates.controller;

import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.service.CertificatesService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @PostMapping("/verification")
    private CustomResponse<Void> verifyCertification(@AuthenticationPrincipal(expression = "user") User user,
                                                     @RequestBody CertificateVertifyRequestDTO dto) {

        return certificatesService.createTrainerCertification(user.getUserId(),dto);

    }

}
