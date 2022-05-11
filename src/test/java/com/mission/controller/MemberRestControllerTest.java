package com.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.domain.Grade;
import com.mission.domain.GradeStaus;
import com.mission.domain.Member;
import com.mission.service.MemberService;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberRestControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private MemberService memberService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
      .addFilters(new CharacterEncodingFilter("UTF-8", true))
      .alwaysDo(print())
      .build();
  }

  @Test
  @DisplayName("회원조회_성공")
  void findMember() throws Exception {
    // given
    final Long findMemberId = 1L;

    // when
    when(memberService.findById(any())).thenReturn(createMemberNothingInterest());

    // then
    this.mockMvc
      .perform(get("/api/v1/member/" + findMemberId)
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("회원전체조회_성공")
  void findMembers() throws Exception {
    // given

    // when
    when(memberService.findAll()).thenReturn(null);

    // then
    this.mockMvc
      .perform(get("/api/v1/member/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("회원등록_성공")
  void creaeMember() throws Exception {
    // given
    MemberCreateVO memberCreateVO = createMemberOfNothingInterestVO();

    // when
    when(memberService.createMember(memberCreateVO)).thenReturn(createMemberNothingInterest());

    // then
    this.mockMvc
      .perform(post("/api/v1/member")
        .content(objectMapper.writeValueAsString(memberCreateVO))
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("회원정보변경_성공")
  void updateMember() throws Exception {
    // given
    MemberUpdateVO memberUpdateVO = createUpdateMemberVO();

    // when
    when(memberService.updateMember(memberUpdateVO)).thenReturn(createMemberNothingInterest());

    // then
    this.mockMvc
      .perform(put("/api/v1/member")
        .content(objectMapper.writeValueAsString(memberUpdateVO))
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("TODO_로그인")
  void loginByEmail() throws Exception {
    // given

    // when

    // then
    this.mockMvc
      .perform(post("/api/v1/member/login/email")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("TODO_로그아웃")
  void logout() throws Exception {
    // given

    // when

    // then
    this.mockMvc
      .perform(post("/api/v1/member/logout")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("TODO_회원정보삭제")
  void deleteAccount() throws Exception {
    // given

    // when

    // then
    this.mockMvc
      .perform(delete("/api/v1/member/delete")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("TODO_회원프로필이미지변경")
  void updateProfileImageUrl() throws Exception {
    // given

    // when

    // then
    this.mockMvc
      .perform(put("/api/v1/member/profileImage")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  private Member createMemberNothingInterest() {
    return Member.builder()
      .id(99L)
      .nickname("bbubbush99")
      .profileImageUrl(null)
      .email("bbubbush99@gmail.com")
      .participationMissions(new ArrayList<>())
      .grade(createBeginnerGrade())
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();
  }

  private Grade createBeginnerGrade() {
    return Grade.builder()
      .id(99L)
      .gradeStaus(GradeStaus.BEGINNER)
      .build();
  }

  private MemberCreateVO createMemberOfInterestVO(List<String> createUserTopic) {
    return new MemberCreateVO("bbubbush99", "bbubbush99@gmail.com", false, createUserTopic);
  }

  private MemberCreateVO createMemberOfNothingInterestVO() {
    return createMemberOfInterestVO(new ArrayList<>());
  }

  private MemberUpdateVO createUpdateMemberVO() {
    return new MemberUpdateVO(99L, "bbubbush", false, null, false, false, createBeginnerGrade(), "bbubbush1@gmail.com", new ArrayList<>());
  }

}