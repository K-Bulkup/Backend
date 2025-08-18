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

// Swagger
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Auth", description = "공통 인증 API")
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
    @ApiOperation(value = "로그인", notes = "이메일/비밀번호 또는 소셜 코드로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @ApiParam(value = "로그인 요청 바디", required = true)
            @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO responseDTO = loginContext.executeLogin(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 2. 회원가입 엔드포인트: AuthService로 위임
    @ApiOperation(value = "회원가입", notes = "일반/소셜 사용자의 회원가입을 처리합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "요청 값 오류"),
            @ApiResponse(code = 409, message = "중복(이미 존재)")
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(
            @ApiParam(value = "회원가입 요청 바디", required = true)
            @Valid @RequestBody SignupRequestDTO dto) {
        SignupResponseDTO responseDTO = authService.signup(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 3. 로그아웃 엔드포인트: AuthService로 위임
    @ApiOperation(value = "로그아웃", notes = "현재 세션/토큰을 무효화합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("로그아웃");
    }

    // 4. 네이버 로그인 시작 엔드포인트
    @ApiOperation(value = "네이버 로그인 시작", notes = "네이버 OAuth 인증 페이지로 리다이렉트합니다.")
    @ApiResponses({
            @ApiResponse(code = 302, message = "리다이렉트")
    })
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
    @ApiOperation(value = "네이버 로그인 콜백", notes = "네이버에서 전달된 code/state로 로그인 처리를 합니다. 이후 프론트로 리다이렉트합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "인증 코드", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "CSRF 방지용 상태값", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 302, message = "리다이렉트")
    })
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
    @ApiOperation(value = "카카오 로그인 시작", notes = "카카오 OAuth 인증 페이지로 리다이렉트합니다.")
    @ApiResponses({
            @ApiResponse(code = 302, message = "리다이렉트")
    })
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
    @ApiOperation(value = "소셜 회원가입 완료", notes = "소셜 로그인 후 추가 정보를 제출하여 회원가입을 마무리합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping("/social-signup-complete")
    public ResponseEntity<LoginResponseDTO> socialSignupComplete(
            @ApiParam(value = "소셜 회원가입 완료 요청 바디", required = true)
            @RequestBody SocialSignUpRequestDTO dto) {
        LoginResponseDTO responseDTO = authService.socialSignUp(dto);
        return ResponseEntity.ok(responseDTO);
    }

    // 8. 카카오 로그인 콜백 엔드포인트
    @ApiOperation(value = "카카오 로그인 콜백", notes = "카카오에서 전달된 code로 로그인 처리를 합니다. 이후 프론트로 리다이렉트합니다.")
    @ApiImplicitParam(name = "code", value = "인증 코드", required = true, dataType = "string", paramType = "query")
    @ApiResponses({
            @ApiResponse(code = 302, message = "리다이렉트")
    })
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

    @ApiOperation(value = "Ping", notes = "서버 상태 확인용 엔드포인트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("/ping")
    private CustomResponse<Void> pingTest(){
        return CustomResponse.success(ResponseCode.SUCCESS);
    }

}
