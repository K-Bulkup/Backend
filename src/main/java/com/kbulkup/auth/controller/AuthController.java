package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO; // SocialSignUpRequestDTO import 추가
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.service.AuthService;
import com.kbulkup.auth.service.LoginContext;
import com.kbulkup.auth.naver.NaverApiClient; // NaverApiClient import 추가
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // GetMapping import 추가
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // RequestParam import 추가
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView; // RedirectView import 추가
import org.slf4j.Logger; // Logger import 추가
import org.slf4j.LoggerFactory; // LoggerFactory import 추가

import javax.servlet.http.HttpServletRequest; // HttpServletRequest import 추가
import javax.validation.Valid;
import java.util.UUID; // UUID import 추가
import com.kbulkup.auth.domain.LoginType; // LoginType import 추가
import com.kbulkup.auth.kakao.KakaoApiClient; // KakaoApiClient import 추가
import java.net.URLEncoder; // URLEncoder import 추가
import java.nio.charset.StandardCharsets; // StandardCharsets import 추가

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class); // Logger 인스턴스 생성

    private final AuthService authService;
    private final LoginContext loginContext;
    private final NaverApiClient naverApiClient; // NaverApiClient 의존성 추가
    private final KakaoApiClient kakaoApiClient; // KakaoApiClient 의존성 추가

    // 1. 로그인 엔드포인트: LoginContext로 위임
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 2. 회원가입 엔드포인트: AuthService로 위임
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO dto) {
        SignupResponseDTO responseDTO = authService.signup(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 3. 로그아웃 엔드포인트: AuthService로 위임
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("로그아웃");
    }

    // 4. 네이버 로그인 시작 엔드포인트
    @GetMapping("/naver/start")
    public RedirectView naverLoginStart(HttpServletRequest request) {
        log.info("Received GET request for /api/common/auth/naver/start"); // 로그 추가
        String state = UUID.randomUUID().toString();
        request.getSession().setAttribute("naver_oauth_state", state);

        String authUrl = String.format(
                "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
                naverApiClient.getClientId(),
                naverApiClient.getRedirectUri(),
                state
        );
        return new RedirectView(authUrl);
    }

    // 5. 네이버 로그인 콜백 엔드포인트
    @GetMapping("/naver/callback")
    public RedirectView naverCallback(@RequestParam String code, @RequestParam String state, HttpServletRequest request) {
        String storedState = (String) request.getSession().getAttribute("naver_oauth_state");
        if (storedState == null || !storedState.equals(state)) {
            throw new IllegalArgumentException("Invalid state parameter");
        }
        request.getSession().removeAttribute("naver_oauth_state"); // 사용 후 제거

        LoginResponseDTO responseDTO = loginContext.executeSocialLogin(LoginType.NAVER, code);

        String redirectUrl = "http://localhost:5173/login"; // 프론트엔드 로그인 페이지 URL
        StringBuilder queryParams = new StringBuilder();

        if (responseDTO.getAccessToken() != null) {
            queryParams.append("accessToken=").append(responseDTO.getAccessToken());
        }

        if (responseDTO.getRoles() != null && !responseDTO.getRoles().isEmpty()) {
            String rolesString = String.join(",", responseDTO.getRoles());
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("role=").append(rolesString);
        }

        if (responseDTO.isNewUser()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("isNewUser=").append(true);
            // 신규 사용자일 경우 추가 정보 전달
            if (responseDTO.getEmail() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("email=").append(URLEncoder.encode(responseDTO.getEmail(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getUsername() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("name=").append(URLEncoder.encode(responseDTO.getUsername(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getProviderId() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("providerId=").append(URLEncoder.encode(responseDTO.getProviderId(), StandardCharsets.UTF_8));
            }
        }

        // loginType 추가
        if (responseDTO.getLoginType() != null) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("loginType=").append(URLEncoder.encode(responseDTO.getLoginType(), StandardCharsets.UTF_8));
        }

        if (queryParams.length() > 0) {
            redirectUrl += "?" + queryParams.toString();
        }

        return new RedirectView(redirectUrl);
    }

    // 6. 카카오 로그인 시작 엔드포인트
    @GetMapping("/kakao/start")
    public RedirectView kakaoLoginStart() {
        log.info("Received GET request for /api/common/auth/kakao/start"); // 로그 추가
        String authUrl = String.format(
                "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoApiClient.getClientId(),
                kakaoApiClient.getRedirectUri()
        );
        return new RedirectView(authUrl);
    }

    // 7. 소셜 회원가입 완료 엔드포인트
    @PostMapping("/social-signup-complete")
    public ResponseEntity<LoginResponseDTO> socialSignupComplete(@RequestBody SocialSignUpRequestDTO dto) {
        LoginResponseDTO responseDTO = authService.socialSignUp(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 8. 카카오 로그인 콜백 엔드포인트
    @GetMapping("/login/oauth2/code/kakao")
    public RedirectView kakaoCallback(@RequestParam String code) {
        LoginResponseDTO responseDTO = loginContext.executeSocialLogin(LoginType.KAKAO, code);

        String redirectUrl = "http://localhost:5173/login"; // 프론트엔드 로그인 페이지 URL
        StringBuilder queryParams = new StringBuilder();

        if (responseDTO.getAccessToken() != null) {
            queryParams.append("accessToken=").append(responseDTO.getAccessToken());
        }

        if (responseDTO.getRoles() != null && !responseDTO.getRoles().isEmpty()) {
            String rolesString = String.join(",", responseDTO.getRoles());
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("role=").append(rolesString);
        }

        if (responseDTO.isNewUser()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("isNewUser=").append(true);
            // 신규 사용자일 경우 추가 정보 전달
            if (responseDTO.getEmail() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("email=").append(URLEncoder.encode(responseDTO.getEmail(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getUsername() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("name=").append(URLEncoder.encode(responseDTO.getUsername(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getProviderId() != null) {
                if (queryParams.length() > 0) queryParams.append("&");
                queryParams.append("providerId=").append(URLEncoder.encode(responseDTO.getProviderId(), StandardCharsets.UTF_8));
            }
        }

        // loginType 추가
        if (responseDTO.getLoginType() != null) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("loginType=").append(URLEncoder.encode(responseDTO.getLoginType(), StandardCharsets.UTF_8));
        }

        if (queryParams.length() > 0) {
            redirectUrl += "?" + queryParams.toString();
        }

        return new RedirectView(redirectUrl);
    }

    @GetMapping("/ping")
    private CustomResponse<Void> pingTest(){
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

}