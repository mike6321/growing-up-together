package com.mission.service;

import com.mission.dto.member.ReqLoginByEmail;
import com.mission.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

  private final JwtAuthenticationProvider provider;
  private final AuthenticationManager authenticationManager;

  public String login(ReqLoginByEmail reqLoginByEmail) {
    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(reqLoginByEmail.getEmail(), reqLoginByEmail.getPassword());
    authenticationManager.authenticate(
      usernamePasswordAuthenticationToken
    );
    return provider.createJwtToken(reqLoginByEmail.getEmail());
  }

}
