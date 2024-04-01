package com.laydowncoding.tickitecking.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

  // Header KEY 값
  public static final String AUTHORIZATION_HEADER = "Authorization";
  // 사용자 권한 값의 KEY
  public static final String AUTHORIZATION_KEY = "Auth";
  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";

  // 토큰 만료시간
  @Value("${jwt.access-token-validity}")
  private long ACCESS_TOKEN_TIME; // 60분

  @Value("${jwt.refresh-token-validity}")
  private long REFRESH_TOKEN_TIME; // 7일

  @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  // 토큰 생성
  public String createAccessToken(Long userId, String username, String role) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  public String createRefreshToken(String role) {
    Date date = new Date();
    String uuid = UUID.randomUUID().toString();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(uuid)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  // header 에서 JWT 가져오기
  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // 토큰 검증
  public int validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return 0;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.");
      return 2;
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return 1;
  }

  public Claims getExpiredTokenClaims(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return null;
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }
}
