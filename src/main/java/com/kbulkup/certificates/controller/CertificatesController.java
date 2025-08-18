package com.kbulkup.certificates.controller;

import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.service.CertificatesService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Trainer Certificates", description = "트레이너 자격증 검증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @ApiOperation(value = "자격증 검증 요청", notes = "트레이너가 자격증 정보를 제출하여 검증을 요청합니다. 민감 정보는 서버 로그/에러에 노출되지 않도록 처리됩니다.")
    @PostMapping("/verification")
    private CustomResponse<Void> verifyCertification(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "자격증 검증 요청 바디(민감 정보 포함)", required = true)
            @RequestBody CertificateVertifyRequestDTO dto) {
        return certificatesService.createTrainerCertification(user.getUserId(), dto);
    }
}
