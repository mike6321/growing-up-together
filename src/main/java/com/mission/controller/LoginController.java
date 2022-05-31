package com.mission.controller;

import com.mission.dto.member.ReqLoginByEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

  @PostMapping("/login/email")
  public ResponseEntity<String> loginByEmail(@RequestBody ReqLoginByEmail reqLoginByEmail) {
    log.info("email :: {}", reqLoginByEmail.getEmail());
    log.info("password :: {}", reqLoginByEmail.getPassword());
    return ResponseEntity.ok("");
  }
}
