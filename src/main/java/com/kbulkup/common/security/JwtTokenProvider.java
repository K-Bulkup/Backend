package com.kbulkup.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // 요청에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    // 인증 정보 반환
    public Authentication getAuthentication(String token) {
        String userPk = jwtUtil.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPk);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 유효한 토큰인지 판단
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // access token 생성
    public String createAccessToken(Long userId, java.util.List<String> roles) {
        return jwtUtil.createToken(userId, roles, false);
    }

    // 임시 토큰 생성
    public String createTempAccessToken(Long userId) {
        return jwtUtil.createToken(userId, null, true);
    }

    public Long getUserId(String token) {
        return jwtUtil.getUserId(token);
    }

    public java.util.List<String> getRoles(String token) {
        return jwtUtil.getRoles(token);
    }
}