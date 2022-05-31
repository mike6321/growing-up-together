package com.mission.service;

import com.mission.domain.Member;
import com.mission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SecurityUserDetailService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return memberRepository.findByEmail(email)
      .map(this::createUser)
      .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
  }

  private UserDetails createUser(Member member) {
    return User.builder()
      .username(member.getEmail())
      .password(member.getPassword())
      .authorities(member.getGrade().getGradeStaus().name())
      .build();
  }

}
