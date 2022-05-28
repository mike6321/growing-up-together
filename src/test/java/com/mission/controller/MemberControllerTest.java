package com.mission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mission.domain.Grade;
import com.mission.domain.GradeStaus;
import com.mission.domain.Member;
import com.mission.dto.member.ReqCreateMember;
import com.mission.dto.member.ReqUpdateMember;
import com.mission.dto.member.ResFindMember;
import com.mission.dto.member.ResModifyMember;
import com.mission.service.MemberService;
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
class MemberControllerTest {

  private final static String HOST_NAME = "/api/member";

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
    final long findMemberId = 1L;

    // when
    when(memberService.findById(any())).thenReturn(ResFindMember.of(createFindMember()));

    // then
    this.mockMvc
      .perform(get(HOST_NAME + "/" + findMemberId)
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
      .perform(get(HOST_NAME + "/list")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  @Test
  @DisplayName("회원등록_성공")
  void createMember() throws Exception {
    // given
    final List<String> createUserTopic = new ArrayList<>(){{
      add("Spring");
      add("Java");
    }};
    ReqCreateMember memberCreateVO = createMemberOfInterestVO(createUserTopic);

    // when
    when(memberService.createMember(memberCreateVO)).thenReturn(createResModifyMember());

    // then
    this.mockMvc
      .perform(post(HOST_NAME)
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
    ReqUpdateMember memberUpdateVO = createUpdateMemberVO();

    // when
    when(memberService.updateMember(memberUpdateVO)).thenReturn(createResModifyMember());

    // then
    this.mockMvc
      .perform(put(HOST_NAME)
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
      .perform(post(HOST_NAME + "/login/email")
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
      .perform(post(HOST_NAME + "/logout")
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
      .perform(delete(HOST_NAME + "/delete")
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
      .perform(put(HOST_NAME + "/profileImage")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
//      .andExpect(content().string(containsString(encodingText)))
    ;
  }

  private ResModifyMember createResModifyMember() {
    return ResModifyMember.of(99L);
  }

  private ReqCreateMember createMemberOfInterestVO(List<String> createUserTopic) {
    return ReqCreateMember
      .builder()
      .nickname("bbubbush99")
      .email("bbubbush99@gmail.com")
      .topicOfInterestAlarm(false)
      .topicOfInterests(createUserTopic)
      .build();
  }

  private ReqUpdateMember createUpdateMemberVO() {
    return ReqUpdateMember
      .builder()
      .id(99L)
      .nickname("bbubbush")
      .email("bbubbush1@gmail.com")
      .topicOfInterestAlarm(true)
      .topicOfInterests(new ArrayList<>())
      .build();
  }

  private Member createFindMember() {
    return Member.builder()
      .id(99L)
      .nickname("bbubbush99")
      .profileImageUrl(null)
      .email("bbubbush99@gmail.com")
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(createBeginnerGrade())
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();
  }

  private Grade createBeginnerGrade() {
    return Grade.builder()
      .id(99L)
      .gradeStaus(GradeStaus.ROLE_BEGINNER)
      .build();
  }
}
