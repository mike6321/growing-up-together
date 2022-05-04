package com.mission.controller;

import com.mission.service.MemberService;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberRestController {
  private final MemberService memberService;

  @GetMapping("/{memberId}")
  public ResponseEntity findMember(@PathVariable Long memberId) {
    return ResponseEntity.ok(memberService.findById(memberId));
  }

  @GetMapping("/list")
  public ResponseEntity findMembers() {
    return ResponseEntity.ok(memberService.findById());
  }

  @PostMapping
  public ResponseEntity createMember(@RequestBody @Valid MemberCreateVO memberCreateVO) {
    return ResponseEntity.ok(memberService.createMember(memberCreateVO));
  }

  @PutMapping
  public ResponseEntity updateMember(@RequestBody @Valid MemberUpdateVO memberUpdateVO) {
    return ResponseEntity.ok(memberService.updateMember(memberUpdateVO));
  }


  /**************
    TODO List
   **************/
  @PostMapping("/login/email")
  public ResponseEntity loginByEmail() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }

  @PostMapping("/logout")
  public ResponseEntity logout() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteAccount() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }
}
