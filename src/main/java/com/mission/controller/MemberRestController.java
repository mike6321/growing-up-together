package com.mission.controller;

import com.mission.domain.Member;
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
  public ResponseEntity<Member> findMember(@PathVariable Long memberId) {
    return ResponseEntity.ok(memberService.findById(memberId));
  }

  @GetMapping("/list")
  public ResponseEntity<Iterable<Member>> findMembers() {
    return ResponseEntity.ok(memberService.findAll());
  }

  @PostMapping
  public ResponseEntity<Member> createMember(@RequestBody @Valid MemberCreateVO memberCreateVO) {
    return ResponseEntity.ok(memberService.createMember(memberCreateVO));
  }

  @PutMapping
  public ResponseEntity<Member> updateMember(@RequestBody @Valid MemberUpdateVO memberUpdateVO) {
    return ResponseEntity.ok(memberService.updateMember(memberUpdateVO));
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
