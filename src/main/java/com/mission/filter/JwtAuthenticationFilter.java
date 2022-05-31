package com.mission.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.dto.member.ReqLoginByEmail;
import com.mission.security.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private static final AntPathRequestMatcher ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/auth/**", "POST");

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
    this.authenticationManager = authenticationManager;
    setRequiresAuthenticationRequestMatcher(ANT_PATH_REQUEST_MATCHER);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    ReqLoginByEmail reqLoginByEmail = null;
    try {
      reqLoginByEmail = new ObjectMapper().readValue(request.getInputStream(), ReqLoginByEmail.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    assert reqLoginByEmail != null;
    final UsernamePasswordAuthenticationToken usernameToken =
        new UsernamePasswordAuthenticationToken(reqLoginByEmail.getEmail(), reqLoginByEmail.getPassword());

    return authenticationManager.authenticate(usernameToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) {

    String token = Jwts.builder()
      .claim(JwtProperties.AUTHORIZATION_KEY.toString(), authResult.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining()))
      .setSubject(authResult.getName())
      .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30L)))
      .signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET_KEY.getValue())
      .compact();

    response.addHeader(JwtProperties.HEADER_NAME.toString(), JwtProperties.TOKEN_PREFIX + token);
  }

}
