package com.mission.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String secret) {
        super(authenticationManager);
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = getUserPasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("SecurityContext에 {} 인증 정보를 저장했습니다. URI : {}", authentication.getName(), request.getRequestURI());
        chain.doFilter(request, response);
    }

    public Authentication getUserPasswordAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);
        Key secretKey = resolveSecretKey();
        Claims claims = getClaims(token, secretKey);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(JwtUtils.AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    private Claims getClaims(String token, Key secretKey) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key resolveSecretKey() {
        byte[] decode = Decoders.BASE64.decode(secret);
        Key secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(JwtUtils.HEADER_STRING);
        return token.replace(JwtUtils.TOKEN_PREFIX, "")
                .trim();
    }

}
