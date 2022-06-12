package com.mission;

import com.annotation.MockMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.dto.login.ReqLogin;
import com.mission.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class TokenTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @DisplayName("Token 확인 테스트 - 등록된 계정이 없는 경우")
    @Test
    void token_test() throws Exception {
        ReqLogin reqLogin = new ReqLogin("test_account@test.com", "test123");
        String content = objectMapper.writeValueAsString(reqLogin);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andDo(print());
    }

    @DisplayName("Token 확인 테스트 - 등록된 계정이 없는 경우")
    @Test
    void token_non_test() throws Exception {
        ReqLogin reqLogin = new ReqLogin("test2_account@test.com", "test123");
        String content = objectMapper.writeValueAsString(reqLogin);
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .with(csrf()))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Authorization"))
                .andDo(print());
    }

}
