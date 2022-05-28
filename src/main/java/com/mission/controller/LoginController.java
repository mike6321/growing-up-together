package com.mission.controller;

import com.mission.dto.login.ReqLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ReqLogin reqLogin) {
        return ResponseEntity.ok(reqLogin);
    }

    @GetMapping("/beginner")
    @PreAuthorize("hasAnyRole('BEGINNER')")
    public ResponseEntity<?> beginner() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/junior")
    @PreAuthorize("hasAnyRole('JUNIOR')")
    public ResponseEntity<?> junior() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/senior")
    @PreAuthorize("hasAnyRole('SENIOR')")
    public ResponseEntity<?> senior() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/etc")
    public ResponseEntity<?> etc() {
        return ResponseEntity.ok(null);
    }

}
