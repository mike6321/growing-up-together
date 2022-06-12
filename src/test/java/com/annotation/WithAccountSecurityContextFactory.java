package com.annotation;

import com.mission.dto.member.ReqCreateMember;
import com.mission.security.JwtUtils;
import com.mission.service.CustomUserDetailsService;
import com.mission.service.MemberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final CustomUserDetailsService customUserDetailsService;
    private final MemberService memberService;
    private static final String PASSWORD = "test123";

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        createMember(withAccount);
        UserDetails principal = customUserDetailsService.loadUserByUsername(withAccount.email());
        UsernamePasswordAuthenticationToken beforeCreateTokenAuthentication = getUsernamePasswordAuthenticationToken(
                PASSWORD,
                principal,
                principal.getAuthorities()
        );
        String token = JwtUtils.createToken(beforeCreateTokenAuthentication);
        UsernamePasswordAuthenticationToken afterCreateTokenAuthentication = getUsernamePasswordAuthenticationToken(
                token,
                principal,
                getGrantedAuthorities(token)
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(afterCreateTokenAuthentication);
        return context;
    }

    private List<SimpleGrantedAuthority> getGrantedAuthorities(String token) {
        Claims claims = JwtUtils.getClaims(token);
        List<SimpleGrantedAuthority> authorities = JwtUtils.getAuthorities(claims);
        return authorities;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String credential,
                                                                                       UserDetails principal,
                                                                                       Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(principal, credential, authorities);
    }

    private void createMember(WithAccount withAccount) {
        ReqCreateMember reqCreateMember = ReqCreateMember.builder()
                .nickname(withAccount.nickname())
                .email(withAccount.email())
                .topicOfInterestAlarm(true)
                .topicOfInterests(List.of("java"))
                .password(PASSWORD)
                .build();
        memberService.createMember(reqCreateMember);
    }

}
