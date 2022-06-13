package com.mission.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null) {
            if (!context.getAuthentication().isAuthenticated()) {
                Authentication authentication = getUserPasswordAuthentication(request);
                context.setAuthentication(authentication);
                log.info("SecurityContext에 {} 인증 정보를 저장했습니다. URI : {}", authentication.getName(), request.getRequestURI());
            }
        }
        chain.doFilter(request, response);
    }

    public Authentication getUserPasswordAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);
        Claims claims = JwtUtils.getClaims(token);
        List<SimpleGrantedAuthority> authorities = JwtUtils.getAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(JwtUtils.HEADER_STRING);
        return token.replace(JwtUtils.TOKEN_PREFIX, "")
                .trim();
    }

}
