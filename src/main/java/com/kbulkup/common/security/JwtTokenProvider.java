package com.kbulkup.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return authentication;
    }

    // 유효한 토큰인지 판단
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // access token 생성
    public String createAccessToken(String email, Long userId, List<String> roles) {
        return jwtUtil.createToken(email, userId, roles, false);
    }

    // 임시 토큰 생성
    public String createTempAccessToken(String email, Long userId) {
        return jwtUtil.createToken(email, userId, null, true);
    }

    public Long getUserId(String token) {
        return jwtUtil.getUserId(token);
    }

    public List<String> getRoles(String token) {
        return jwtUtil.getRoles(token);
    }
}