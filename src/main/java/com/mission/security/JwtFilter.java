package com.mission.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.dto.login.ReqLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final String secret;
    private final long tokenValidityInSeconds;
    private final Key key;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login",
            "POST"
    );

    public JwtFilter(AuthenticationManager authenticationManager,
                     String secret,
                     long tokenValidityInSeconds) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        key = generateKey(secret);
    }

    private Key generateKey(String secret) {
        byte[] decode = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        ReqLogin reqLogin = null;
        try {
            reqLogin = new ObjectMapper().readValue(request.getInputStream(), ReqLogin.class);
        } catch (IOException e) {
            log.warn("ReqLogin cast 중 오류가 발생하였습니다.");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                reqLogin.getEmail(),
                reqLogin.getPassword()
        );
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        String token = createToken(authentication);
        response.addHeader(JwtUtils.HEADER_STRING, JwtUtils.TOKEN_PREFIX + token);
    }

    private String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long nowPlus = (new Date()).getTime() + this.tokenValidityInSeconds;
        Date validity = new Date(nowPlus);
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(JwtUtils.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

}
