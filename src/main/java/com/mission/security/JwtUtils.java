package com.mission.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "auth";
    public static final String SECRET = "Z3Jvd2luZy11cC10b2dldGhlci10ZXN0LXNlY3JldC1rZXktdGVzdC10ZXN0LXRlc3QtdGVzdC10ZXN0LXRlc3QtdGVzdC10ZXN0LXRlc3Q=";
    public static final long TOKEN_VALIDITY_IN_SECONDS = 86400;


    private static Key generateKey(String secret) {
        byte[] decode = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }

    public static String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long nowPlus = (new Date()).getTime() + TOKEN_VALIDITY_IN_SECONDS;
        Date validity = new Date(nowPlus);
        Key key = generateKey(SECRET);
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(JwtUtils.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public static Claims getClaims(String token) {
        Key secretKey = resolveSecretKey();
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | MalformedJwtException exception) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException exception) {
            log.warn("만료된 JWT 서명입니다.");
        } catch (UnsupportedJwtException exception) {
            log.warn("지원되지 않는 JWT 서명입니다.");
        } catch (IllegalArgumentException exception) {
            log.warn("JWT 토큰이 잘못되었습니다.");
        }
        return claims;
    }

    public static Key resolveSecretKey() {
        byte[] decode = Decoders.BASE64.decode(JwtUtils.SECRET);
        return Keys.hmacShaKeyFor(decode);
    }

    public static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get(JwtUtils.AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
