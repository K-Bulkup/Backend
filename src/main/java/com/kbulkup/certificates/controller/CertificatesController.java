package com.kbulkup.certificates.controller;

import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.service.CertificatesService;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(
            value = "자격증 검증 요청",
            notes = "트레이너가 자격증 정보를 제출하여 검증을 요청합니다. "
                    + "민감 정보(생년월일, 자격번호 등)는 서버 로그에 기록하지 않도록 주의하세요."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping("/verification")
    private CustomResponse<Void> verifyCertification(
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user,
            @ApiParam(value = "자격증 검증 요청 바디", required = true)
            @RequestBody CertificateVertifyRequestDTO dto) {

        return certificatesService.createTrainerCertification(user.getUserId(), dto);
    }
}
