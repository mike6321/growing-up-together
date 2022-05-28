package com.mission.service;

import com.mission.domain.Member;
import com.mission.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(member -> createUser(email, member))
                .orElseThrow(EntityNotFoundException::new);
    }

    private UserDetails createUser(String email, Member member) {
        String grade = member.getGrade()
                .getGradeStaus()
                .name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(grade);
        return User.builder()
                .username(email)
                .password(member.getPassword())
                .authorities(authority)
                .build();
    }

}
