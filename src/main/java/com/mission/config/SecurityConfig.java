package com.mission.config;

import com.mission.security.JwtAuthorizationFilter;
import com.mission.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private final String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private final long tokenValidityInSeconds;
    private final SecurityConfigFactory securityConfigFactory;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/api/member/create");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .expressionHandler(securityConfigFactory.expressionHandler());
        http.csrf().disable();
        http.addFilterAfter(new JwtFilter(authenticationManager(), secret, tokenValidityInSeconds), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), secret));
    }

}
