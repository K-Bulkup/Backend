package com.kbulkup.certificates.service;

import com.kbulkup.certificates.dto.request.CertificateCreateRequestDTO;
import com.kbulkup.certificates.dto.request.CertificateVertifyRequestDTO;
import com.kbulkup.certificates.mapper.CertificatesMapper;
import com.kbulkup.common.exception.CertificatesException;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mockStatic;

/**
 * CertificatesServiceImpl 단위 테스트
 * 이 클래스는 자격증 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - 트레이너 자격증 생성
 * - 외부 API를 통한 자격증 유효성 검증
 * - WebClient를 이용한 비동기 통신 처리
 * - 예외 상황 및 재시도 로직
 */
@ExtendWith(MockitoExtension.class)
class CertificatesServiceImplTest {

    @Mock
    private CertificatesMapper certificatesMapper;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CertificatesServiceImpl certificatesService;

    @Test
    @DisplayName("트레이너 자격증 생성 - 성공")
    void createTrainerCertification_Success() {
        // Given
        Long trainerId = 1L;
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        // WebClient 모킹을 위한 정적 모킹
        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            // 성공적인 검증 응답 설정
            Map<String, Object> validResponse = new HashMap<>();
            validResponse.put("ablNoOrgInfo", "valid_data");

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(validResponse));

            // When
            CustomResponse<Void> result = certificatesService.createTrainerCertification(trainerId, dto);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getStatus()).isEqualTo(ResponseCode.SUCCESS.getHttpStatus().value());
            assertThat(result.getName()).isEqualTo(ResponseCode.SUCCESS.name());

            // 자격증 생성 메서드 호출 검증
            ArgumentCaptor<CertificateCreateRequestDTO> captor = ArgumentCaptor.forClass(CertificateCreateRequestDTO.class);
            then(certificatesMapper).should().createTrainerCertification(captor.capture());

            CertificateCreateRequestDTO capturedDto = captor.getValue();
            assertThat(capturedDto.getTrainerId()).isEqualTo(trainerId);
            assertThat(capturedDto.getCertType()).isEqualTo("투자상담사");
            assertThat(capturedDto.getCertNumber()).isEqualTo("12345");
        }
    }

    @Test
    @DisplayName("트레이너 자격증 검증 - 성공")
    void vertifyTrainerCertification_Success() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            // 성공적인 검증 응답 설정
            Map<String, Object> validResponse = new HashMap<>();
            validResponse.put("ablNoOrgInfo", "valid_certificate_data");

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(validResponse));

            // When & Then
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectNext(true)
                    .verifyComplete();
        }
    }

    @Test
    @DisplayName("트레이너 자격증 검증 - 유효하지 않은 자격증")
    void vertifyTrainerCertification_InvalidCertificate_ThrowsException() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("invalid")
                .birth("19900101")
                .successCtfyNoLic("INVALID")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            // 유효하지 않은 응답 설정 (ablNoOrgInfo가 없음)
            Map<String, Object> invalidResponse = new HashMap<>();
            invalidResponse.put("error", "not_found");

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(invalidResponse));

            // When & Then
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectError(CertificatesException.class)
                    .verify();
        }
    }

    @Test
    @DisplayName("트레이너 자격증 검증 - ablNoOrgInfo가 null인 경우")
    void vertifyTrainerCertification_NullAblNoOrgInfo_ThrowsException() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            // ablNoOrgInfo가 null인 응답
            Map<String, Object> responseWithNullInfo = new HashMap<>();
            responseWithNullInfo.put("ablNoOrgInfo", null);

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(responseWithNullInfo));

            // When & Then
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectError(CertificatesException.class)
                    .verify();
        }
    }

    @Test
    @DisplayName("트레이너 자격증 검증 - 네트워크 오류")
    void vertifyTrainerCertification_NetworkError_ThrowsException() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.error(new RuntimeException("Network error")));

            // When & Then
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectError(CertificatesException.class)
                    .verify();
        }
    }

    @Test
    @DisplayName("트레이너 자격증 생성 - 검증 실패 시 DB 저장 안됨")
    void createTrainerCertification_VerificationFails_NoDbSave() {
        // Given
        Long trainerId = 1L;
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("invalid")
                .birth("19900101")
                .successCtfyNoLic("INVALID")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            // 검증 실패 응답
            Map<String, Object> invalidResponse = new HashMap<>();

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(invalidResponse));

            // When & Then
            assertThatThrownBy(() -> certificatesService.createTrainerCertification(trainerId, dto))
                    .isInstanceOf(RuntimeException.class); // block()에서 발생하는 예외

            // DB 저장이 호출되지 않았는지 검증
            then(certificatesMapper).should(never()).createTrainerCertification(any(CertificateCreateRequestDTO.class));
        }
    }

    @Test
    @DisplayName("WebClient 설정 검증")
    void vertifyTrainerCertification_WebClientConfiguration() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            Map<String, Object> validResponse = new HashMap<>();
            validResponse.put("ablNoOrgInfo", "valid_data");

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(validResponse));

            // When
            certificatesService.vertifyTrainerCertification(dto).block();

            // Then
            // WebClient 빌더의 baseUrl 설정 검증
            then(builder).should().baseUrl("https://license.kofia.or.kr/scsInquiry/ablNoOrg/ajax/getAblNoOrgList.do");

            // Content-Type 설정 검증
            then(requestBodyUriSpec).should().contentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
    }

    @Test
    @DisplayName("Form 데이터 구성 검증")
    void vertifyTrainerCertification_FormDataConstruction() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("TEST123")
                .birth("19851225")
                .successCtfyNoLic("LICENSE456")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            Map<String, Object> validResponse = new HashMap<>();
            validResponse.put("ablNoOrgInfo", "valid_data");

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.just(validResponse));

            // When
            certificatesService.vertifyTrainerCertification(dto).block();

            // Then
            ArgumentCaptor<MultiValueMap<String, String>> formDataCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
            then(requestBodySpec).should().bodyValue(formDataCaptor.capture());

            MultiValueMap<String, String> capturedFormData = formDataCaptor.getValue();
            assertThat(capturedFormData.getFirst("successDocNo")).isEqualTo("TEST123");
            assertThat(capturedFormData.getFirst("birth")).isEqualTo("19851225");
            assertThat(capturedFormData.getFirst("successCtfyNoLic")).isEqualTo("LICENSE456");
        }
    }

    @Test
    @DisplayName("재시도 로직 검증 - 단순 실패 케이스")
    void vertifyTrainerCertification_RetryLogic() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);

            // 지속적으로 네트워크 오류 발생
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.error(new RuntimeException("Network error")));

            // When & Then
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectError(CertificatesException.class)
                    .verify();
        }
    }

    @Test
    @DisplayName("타임아웃 로직 검증")
    void vertifyTrainerCertification_TimeoutLogic() {
        // Given
        CertificateVertifyRequestDTO dto = CertificateVertifyRequestDTO.builder()
                .certType("투자상담사")
                .successDocNo("12345")
                .birth("19900101")
                .successCtfyNoLic("LIC123")
                .build();

        try (MockedStatic<WebClient> webClientMock = mockStatic(WebClient.class)) {
            WebClient.Builder builder = mock(WebClient.Builder.class);
            webClientMock.when(WebClient::builder).thenReturn(builder);
            given(builder.baseUrl(anyString())).willReturn(builder);
            given(builder.build()).willReturn(webClient);

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.bodyValue(any(MultiValueMap.class))).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(responseSpec);

            // 매우 느린 응답 시뮬레이션
            given(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                    .willReturn(Mono.never()); // 무한히 대기

            // When & Then - 타임아웃 발생 예상
            StepVerifier.create(certificatesService.vertifyTrainerCertification(dto))
                    .expectError(CertificatesException.class)
                    .verify();
        }
    }
}
