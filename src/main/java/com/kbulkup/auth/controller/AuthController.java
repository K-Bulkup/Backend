package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.auth.dto.request.SocialSignUpRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.auth.dto.response.SignupResponseDTO;
import com.kbulkup.auth.service.AuthService;
import com.kbulkup.auth.service.LoginContext;
import com.kbulkup.auth.naver.NaverApiClient;
import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.kakao.KakaoApiClient;
import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Api(tags = "Auth", description = "공통 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final LoginContext loginContext;
    private final NaverApiClient naverApiClient;
    private final KakaoApiClient kakaoApiClient;

    @ApiOperation(value = "로그인", notes = "이메일/비밀번호 또는 소셜 코드로 로그인합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @ApiParam(value = "로그인 요청 바디", required = true)
            @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(loginContext.executeLogin(dto));
    }

    @ApiOperation(value = "회원가입", notes = "일반/소셜 사용자의 회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(
            @ApiParam(value = "회원가입 요청 바디", required = true)
            @Valid @RequestBody SignupRequestDTO dto) {
        return ResponseEntity.ok(authService.signup(dto));
    }

    @ApiOperation(value = "로그아웃", notes = "현재 세션/토큰을 무효화합니다.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("로그아웃");
    }

    @ApiOperation(value = "네이버 로그인 시작", notes = "네이버 OAuth 인증 페이지로 리다이렉트합니다.")
    @ApiResponse(code = 302, message = "리다이렉트")
    @GetMapping("/naver/start")
    public RedirectView naverLoginStart(HttpServletRequest request) {
        log.info("GET /api/common/auth/naver/start");
        String state = UUID.randomUUID().toString();
        request.getSession().setAttribute("naver_oauth_state", state);
        String authUrl = String.format(
                "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
                naverApiClient.getClientId(), naverApiClient.getRedirectUri(), state);
        return new RedirectView(authUrl);
    }

    @ApiOperation(value = "네이버 로그인 콜백", notes = "네이버 code/state로 로그인 처리 후 프론트로 리다이렉트합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "인증 코드", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "상태값", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponse(code = 302, message = "리다이렉트")
    @GetMapping("/naver/callback")
    public RedirectView naverCallback(@RequestParam String code, @RequestParam String state, HttpServletRequest request) {
        String storedState = (String) request.getSession().getAttribute("naver_oauth_state");
        if (storedState == null || !storedState.equals(state)) {
            throw new IllegalArgumentException("Invalid state parameter");
        }
        request.getSession().removeAttribute("naver_oauth_state");

        LoginResponseDTO responseDTO = loginContext.executeSocialLogin(LoginType.NAVER, code);

        String redirectUrl = "http://localhost:5173/login";
        StringBuilder queryParams = new StringBuilder();

        if (responseDTO.getAccessToken() != null) queryParams.append("accessToken=").append(responseDTO.getAccessToken());
        if (responseDTO.getRoles() != null && !responseDTO.getRoles().isEmpty()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("role=").append(String.join(",", responseDTO.getRoles()));
        }
        if (responseDTO.isNewUser()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("isNewUser=true");
            if (responseDTO.getEmail() != null) {
                queryParams.append("&email=").append(URLEncoder.encode(responseDTO.getEmail(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getUsername() != null) {
                queryParams.append("&name=").append(URLEncoder.encode(responseDTO.getUsername(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getProviderId() != null) {
                queryParams.append("&providerId=").append(URLEncoder.encode(responseDTO.getProviderId(), StandardCharsets.UTF_8));
            }
        }
        if (responseDTO.getLoginType() != null) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("loginType=").append(URLEncoder.encode(responseDTO.getLoginType(), StandardCharsets.UTF_8));
        }
        if (queryParams.length() > 0) redirectUrl += "?" + queryParams;

        return new RedirectView(redirectUrl);
    }

    @ApiOperation(value = "카카오 로그인 시작", notes = "카카오 OAuth 인증 페이지로 리다이렉트합니다.")
    @ApiResponse(code = 302, message = "리다이렉트")
    @GetMapping("/kakao/start")
    public RedirectView kakaoLoginStart() {
        log.info("GET /api/common/auth/kakao/start");
        String authUrl = String.format(
                "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                kakaoApiClient.getClientId(), kakaoApiClient.getRedirectUri());
        return new RedirectView(authUrl);
    }

    @ApiOperation(value = "소셜 회원가입 완료", notes = "소셜 로그인 후 추가 정보를 제출하여 회원가입을 마무리합니다.")
    @PostMapping("/social-signup-complete")
    public ResponseEntity<LoginResponseDTO> socialSignupComplete(
            @ApiParam(value = "소셜 회원가입 완료 요청 바디", required = true)
            @RequestBody SocialSignUpRequestDTO dto) {
        return ResponseEntity.ok(authService.socialSignUp(dto));
    }

    @ApiOperation(value = "카카오 로그인 콜백", notes = "카카오 code로 로그인 처리 후 프론트로 리다이렉트합니다.")
    @ApiImplicitParam(name = "code", value = "인증 코드", required = true, dataType = "string", paramType = "query")
    @ApiResponse(code = 302, message = "리다이렉트")
    @GetMapping("/login/oauth2/code/kakao")
    public RedirectView kakaoCallback(@RequestParam String code) {
        LoginResponseDTO responseDTO = loginContext.executeSocialLogin(LoginType.KAKAO, code);

        String redirectUrl = "http://localhost:5173/login";
        StringBuilder queryParams = new StringBuilder();

        if (responseDTO.getAccessToken() != null) queryParams.append("accessToken=").append(responseDTO.getAccessToken());
        if (responseDTO.getRoles() != null && !responseDTO.getRoles().isEmpty()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("role=").append(String.join(",", responseDTO.getRoles()));
        }
        if (responseDTO.isNewUser()) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("isNewUser=true");
            if (responseDTO.getEmail() != null) {
                queryParams.append("&email=").append(URLEncoder.encode(responseDTO.getEmail(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getUsername() != null) {
                queryParams.append("&name=").append(URLEncoder.encode(responseDTO.getUsername(), StandardCharsets.UTF_8));
            }
            if (responseDTO.getProviderId() != null) {
                queryParams.append("&providerId=").append(URLEncoder.encode(responseDTO.getProviderId(), StandardCharsets.UTF_8));
            }
        }
        if (responseDTO.getLoginType() != null) {
            if (queryParams.length() > 0) queryParams.append("&");
            queryParams.append("loginType=").append(URLEncoder.encode(responseDTO.getLoginType(), StandardCharsets.UTF_8));
        }
        if (queryParams.length() > 0) redirectUrl += "?" + queryParams;

        return new RedirectView(redirectUrl);
    }

    @ApiOperation(value = "Ping", notes = "서버 상태 확인용 엔드포인트")
    @GetMapping("/ping")
    private CustomResponse<Void> pingTest() {
        return CustomResponse.success(ResponseCode.SUCCESS);
    }
}
