package com.mission.service;

import com.mission.domain.*;
import com.mission.repository.GradeRepository;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import com.mission.vo.MemberCreateVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {
  @InjectMocks
  private MemberService memberService;
  @Mock
  private MemberRepository memberRepository;
  @Mock
  private GradeRepository gradeRepository;
  @Mock
  private TopicOfInterestRepository topicOfInterestRepository;


  @Test
  @DisplayName("회원전체조회")
  void findAllMember() {
    // given

    // when
    Iterable<Member> findMembers = memberService.findAll();

    // then
    assertNotNull(findMembers);
  }

  @Test
  @DisplayName("회원조회_기본")
  public void findMember() {
    // given
    final long memberId = 2L;
    Member createMember = Member.builder()
      .id(memberId)
      .nickname("bbubbush")
      .profileImageUrl(null)
      .email("bbubbush@gmail.com")
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(null)
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();

    // when
    when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(createMember));
    Member findMember = memberService.findById(memberId);

    // then
    assertNotNull(findMember);
    assertNotNull(findMember.getId());
    assertNotEquals(0L, findMember.getId());
    assertEquals(memberId, findMember.getId());

    assertNotNull(findMember.getNickname());
    assertEquals(createMember.getNickname(), findMember.getNickname());

    assertNull(findMember.getProfileImageUrl());
    assertEquals(createMember.getProfileImageUrl(), findMember.getProfileImageUrl());

    assertNotNull(findMember.getParticipationMissions());
    assertEquals(createMember.getParticipationMissions(), findMember.getParticipationMissions());
    assertEquals(0, findMember.getParticipationMissions().size());

    assertNotNull(findMember.getTopicOfInterests());
    assertEquals(createMember.getTopicOfInterests(), findMember.getTopicOfInterests());
    assertEquals(0, findMember.getTopicOfInterests().size());

    assertNull(findMember.getGrade());
    assertFalse(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());

  }

  @Test
  @DisplayName("회원등록_실패_이메일중복")
  public void createMemberFail1() {
    // given
    final String createUserEmail = "bbubbush1@gmail.com";
    final String expectedMessage = "중복된 이메일 입니다.";
    Member findMember = Member.builder()
      .id(5L)
      .nickname("bbubbush")
      .profileImageUrl(null)
      .email("bbubbush99@gmail.com")
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(Grade.builder().id(4L).gradeStaus(GradeStaus.BEGINNER).build())
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();
    MemberCreateVO createVO = new MemberCreateVO("nickname", "bbubbush1@gmail.com", false, new ArrayList<>());

    // when
    when(memberRepository.findByEmail(createUserEmail)).thenReturn(Optional.ofNullable(findMember));
    RuntimeException duplicateEmailException = assertThrows(RuntimeException.class, () -> memberService.createMember(createVO));

    // then
    assertEquals(expectedMessage, duplicateEmailException.getMessage());

  }

  @Test
  @DisplayName("회원등록_성공_회원관심항목없음")
  public void createMemberSuccess1() {
    // given
    final String createUserNickname = "bbubbush99";
    final String createUserEmail = "bbubbush99@gmail.com";
    MemberCreateVO createVO = new MemberCreateVO(createUserNickname, createUserEmail, false, new ArrayList<>());
    Grade createGrade = Grade.builder().id(99L).gradeStaus(GradeStaus.BEGINNER).build();
    Member createMember = Member.builder()
      .id(99L)
      .nickname(createUserNickname)
      .profileImageUrl(null)
      .email(createUserEmail)
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(createGrade)
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();

    // when
    when(memberRepository.findByEmail(createUserEmail)).thenReturn(Optional.ofNullable(null));
    when(memberRepository.save(any())).thenReturn(createMember);

    Member saveMember = memberService.createMember(createVO);

    // then
    assertNotNull(saveMember.getNickname());
    assertEquals(createUserNickname, saveMember.getNickname());

    assertNull(saveMember.getProfileImageUrl());

    assertNotNull(saveMember.getParticipationMissions());

    assertNotNull(saveMember.getTopicOfInterests());

    assertNotNull(saveMember.getGrade());
    assertEquals(GradeStaus.BEGINNER, saveMember.getGrade().getGradeStaus());
    assertEquals(0L, saveMember.getGrade().getPoint());

    assertFalse(saveMember.isTopicOfInterestAlarm());
    assertFalse(saveMember.isEmailAuthenticate());
    assertFalse(saveMember.isWithdrawal());

  }

  @Test
  @DisplayName("회원등록_성공_회원관심항목있음")
  public void createMemberSuccess2() {
    // given
    final String createUserNickname = "bbubbush99";
    final String createUserEmail = "bbubbush99@gmail.com";
    final Grade createGrade = Grade.builder().id(99L).gradeStaus(GradeStaus.BEGINNER).build();
    final List<String> createUserTopic = new ArrayList<>(){{
      add("Spring");
      add("Java");
    }};
    MemberCreateVO createVO = new MemberCreateVO(createUserNickname, createUserEmail, false, createUserTopic);
    Member createMember = Member.builder()
      .id(99L)
      .nickname(createUserNickname)
      .profileImageUrl(null)
      .email(createUserEmail)
      .participationMissions(new ArrayList<>())
      .grade(createGrade)
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();

    // when
    when(memberRepository.findByEmail(createUserEmail)).thenReturn(Optional.ofNullable(null));
    when(topicOfInterestRepository.findByName("Spring")).thenReturn(TopicOfInterest.builder().topicOfInterestId(0L).name("Spring").build());
    when(topicOfInterestRepository.findByName("Java")).thenReturn(TopicOfInterest.builder().topicOfInterestId(1L).name("Java").build());
    when(memberRepository.save(any())).thenReturn(createMember);

    Member saveMember = memberService.createMember(createVO);

    // then
    assertNotNull(saveMember.getNickname());
    assertEquals(createUserNickname, saveMember.getNickname());

    assertNull(saveMember.getProfileImageUrl());

    assertNotNull(saveMember.getParticipationMissions());

    assertNotNull(saveMember.getTopicOfInterests());

    assertNotNull(saveMember.getGrade());
    assertEquals(createGrade.getGradeStaus(), saveMember.getGrade().getGradeStaus());
    assertEquals(createGrade.getPoint(), saveMember.getGrade().getPoint());

    assertFalse(saveMember.isTopicOfInterestAlarm());
    assertFalse(saveMember.isEmailAuthenticate());
    assertFalse(saveMember.isWithdrawal());

  }

}