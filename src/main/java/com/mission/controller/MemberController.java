package com.mission.controller;

import com.mission.dto.member.ReqCreateMember;
import com.mission.dto.member.ReqUpdateMember;
import com.mission.dto.member.ResFindMember;
import com.mission.dto.member.ResModifyMember;
import com.mission.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/{memberId}")
  public ResponseEntity<ResFindMember> findMember(@PathVariable Long memberId) {
    return ResponseEntity.ok(memberService.findById(memberId));
  }

  @GetMapping("/list")
  public ResponseEntity<List<ResFindMember>> findMembers() {
    return ResponseEntity.ok(memberService.findAll());
  }

  @PostMapping
  public ResponseEntity<ResModifyMember> createMember(@RequestBody @Valid ReqCreateMember memberCreateVO) {
    return ResponseEntity.ok(memberService.createMember(memberCreateVO));
  }

  @PutMapping
  public ResponseEntity<ResModifyMember> updateMember(@RequestBody @Valid ReqUpdateMember reqUpdateMember) {
    return ResponseEntity.ok(memberService.updateMember(reqUpdateMember));
  }

  /**************
   * TODO List
   **************/
  @PostMapping("/login/email")
  public ResponseEntity<String> loginByEmail() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteAccount() {
    // TODO bbubbush :: 시큐리티 설정 이후 개발 예정
    return ResponseEntity.ok("");
  }

  @PutMapping("/profileImage")
  public ResponseEntity<String> updateProfileImageUrl() {
    // TODO bbubbush :: 파일 업로드 적용 후 개발 예정
    return ResponseEntity.ok("");
  }

}
