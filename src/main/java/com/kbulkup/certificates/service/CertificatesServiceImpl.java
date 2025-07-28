package com.kbulkup.certificates.service;

import com.kbulkup.certificates.dto.request.CertificateCreateRequestDTO;
import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.mapper.CertificatesMapper;
import com.kbulkup.common.exception.CertificatesException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificatesServiceImpl implements CertificatesService {

    private final CertificatesMapper certificatesMapper;

    @Override
    @Transactional
    public CustomResponse<Void> createTrainerCertification(Long trainerId, CertificateVertifyRequestDTO dto) {

        //자격증 유효성 검증
        vertifyTrainerCertification(dto).block();

        CertificateCreateRequestDTO certificateCreateRequestDTO = CertificateCreateRequestDTO.create(trainerId,dto);
        certificatesMapper.createTrainerCertification(certificateCreateRequestDTO);

        return CustomResponse.success(ResponseCode.SUCCESS);

    }

    public Mono<Boolean> vertifyTrainerCertification(CertificateVertifyRequestDTO dto){

        // Form data 구성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("successDocNo", dto.getSuccessDocNo());
        formData.add("birth", dto.getBirth());
        formData.add("successCtfyNoLic", dto.getSuccessCtfyNoLic());

        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://license.kofia.or.kr/scsInquiry/ablNoOrg/ajax/getAblNoOrgList.do")
                .build();

        return webClient
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> {
                    boolean isValid = response.containsKey("ablNoOrgInfo") &&
                            response.get("ablNoOrgInfo") != null;
                    if (!isValid) {
                        throw new CertificatesException(ResponseCode.TRAINER_CERTIFICATES_VALIDATION_FAILED);
                    }
                    return true;
                })
                .timeout(Duration.ofSeconds(15))
                .retry(2)
                .onErrorMap(throwable -> {
                    if (throwable instanceof CertificatesException) {
                        return throwable;
                    }
                    return new CertificatesException(ResponseCode.TRAINER_CERTIFICATES_VALIDATION_FAILED);
                });
    }
}
