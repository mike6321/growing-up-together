package com.mission.config;

import com.mission.filter.JwtAuthenticationFilter;
import com.mission.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
      .authorizeRequests()
        .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
        .anyRequest().authenticated();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
