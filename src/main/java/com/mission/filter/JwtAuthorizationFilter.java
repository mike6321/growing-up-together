package com.mission.filter;

import com.mission.security.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    final String token = getJwtTokenFromRequestHeader(request);

    if (token == null) {
      chain.doFilter(request, response);
      return;
    }

    final Authentication authentication = getAuthentication(token);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    chain.doFilter(request, response);
  }

  private String getJwtTokenFromRequestHeader(HttpServletRequest servletRequest) {
    return servletRequest.getHeader("Authorization");
  }

  private Authentication getAuthentication(String token) {
    final Jws<Claims> claims = getClaims(token);

    final List<SimpleGrantedAuthority> authorities = getAuthorization(claims);
    final String userEmail = getSubjectByToken(claims);
    return new UsernamePasswordAuthenticationToken(userEmail, "", authorities);
  }

  private Jws<Claims> getClaims(String token) {
    Jws<Claims> claims = null;
    try{
      claims = Jwts.parser()
        .setSigningKey(JwtProperties.SECRET_KEY.toString())
        .parseClaimsJws(token.replace(JwtProperties.TOKEN_PREFIX.toString(), ""));
    } catch (ExpiredJwtException e) {
      log.error("토큰 만료만료 :: {}", e.getMessage());
    }
    return claims;
  }

  private List<SimpleGrantedAuthority> getAuthorization(Jws<Claims> claimsJws) {
    final String authorizationText = (String) claimsJws.getBody().get(JwtProperties.AUTHORIZATION_KEY.toString());
    return Stream.of(authorizationText.split(","))
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }

  private String getSubjectByToken(Jws<Claims> claimsJws) {
    return claimsJws
      .getBody()
      .getSubject()
      ;
  }

}
