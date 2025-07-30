package com.kbulkup.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private byte[] secretKeyBytes;

    // 최종 인증 토큰 유효시간: 30분
    private final long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKeyBytes = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    }

    // 최종 인증 JWT 생성
    public String createAccessToken(Long userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId)); // userId를 subject로 사용
        claims.put("userId", userId);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKeyBytes).build()
                .parseClaimsJws(token).getBody().get("userId", Long.class);
    }

    // 최종 인증 JWT에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 최종 인증 JWT에서 회원 정보 추출 (userPk = userId)
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKeyBytes).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKeyBytes).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 임시 토큰 유효시간: 5분
    private final long tempTokenValidTime = 5 * 60 * 1000L;

    // 임시 JWT 생성 (소셜 로그인 시 추가 정보 입력 화면으로 넘어갈 때 사용)
    public String createTempAccessToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("userId", userId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tempTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public Long getUserIdFromTempToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeyBytes)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", Long.class);
        } catch (Exception e) {
            // 토큰 파싱 실패 시 null 또는 예외 처리
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKeyBytes)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (List<String>) claims.get("roles");
    }

}