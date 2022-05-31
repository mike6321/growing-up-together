package com.mission.security;

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

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

  private String secretKey = "1234567890123456";

  private final UserDetailsService userDetailsService;

  public String createJwtToken(String userEmail) {
    return Jwts.builder()
      .setSubject(userEmail)
      .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
      .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)))
      .signWith(SignatureAlgorithm.HS512, secretKey)
      .compact();
  }

  public String getUserPk(String jwtToken) {
    return Jwts.parser()
      .setSigningKey(secretKey)
      .parseClaimsJws(jwtToken)
      .getBody()
      .getSubject();
  }

  public String getJwtTokenFromRequestHeader(HttpServletRequest servletRequest) {
    return servletRequest.getHeader("Authorization");
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

}
