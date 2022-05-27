package com.mission.controller;

import com.mission.dto.login.ReqLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ReqLogin reqLogin) {

        return ResponseEntity.ok(null);
    }

}
