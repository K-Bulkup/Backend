package com.kbulkup.common.security;

import com.kbulkup.auth.dto.SocialUserInfoDto;
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
    // 임시 Pre-Auth 토큰 유효시간: 10분
    private final long preAuthTokenValidTime = 10 * 60 * 1000L;


    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKeyBytes = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    }

    // 최종 인증 JWT 생성
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    // 신규 소셜 유저를 위한 임시 토큰 생성
    public String createPreAuthToken(SocialUserInfoDto socialUserInfo) {
        Claims claims = Jwts.claims();
        claims.put("email", socialUserInfo.getEmail());
        claims.put("providerId", socialUserInfo.getProviderId());
        claims.put("loginType", socialUserInfo.getLoginType());
        claims.put("username", socialUserInfo.getUsername());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + preAuthTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    // 최종 인증 JWT에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 최종 인증 JWT에서 회원 정보 추출 (userPk = email)
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKeyBytes).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // 임시 토큰에서 신규 사용자 정보 추출
    public SocialUserInfoDto getSocialUserInfoFromPreAuthToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKeyBytes).build()
                .parseClaimsJws(token).getBody();
        return SocialUserInfoDto.builder()
                .email(claims.get("email", String.class))
                .providerId(claims.get("providerId", String.class))
                .loginType(claims.get("loginType", String.class))
                .username(claims.get("username", String.class))
                .build();
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
}