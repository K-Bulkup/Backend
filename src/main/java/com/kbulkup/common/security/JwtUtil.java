package com.kbulkup.common.security;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("classpath:application-secret.properties")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    private final long accessTokenValidity = 30 * 60 * 1000L; // 30분
    private final long tempTokenValidity = 5 * 60 * 1000L;    // 5분

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long userId, List<String> roles, boolean isTemp) {
        long validity = isTemp ? tempTokenValidity : accessTokenValidity;
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("userId", userId);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ResponseCode.AUTH_JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new AuthException(ResponseCode.AUTH_JWT_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new AuthException(ResponseCode.AUTH_JWT_MALFORMED);
        } catch (SignatureException e) {
            throw new AuthException(ResponseCode.AUTH_JWT_INVALID_SIGNATURE);
        } catch (Exception e) {
            throw new AuthException(ResponseCode.AUTH_JWT_PARSING_FAILED);
        }
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Long getUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        return (List<String>) getClaims(token).get("roles");
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (AuthException e) {
            return false;
        }
    }
}