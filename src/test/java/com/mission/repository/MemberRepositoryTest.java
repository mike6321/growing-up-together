package com.mission.repository;

import com.mission.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) @DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("회원조회_관심항목있음")
  void findMemberInterestSpringAndJava() {
    // given
    final long expectedId = 7L;
    final String expectedNickname = "bbubbush";
    final String expectedNameOfTopicOfInterest = "Spring,Java";
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();

    // when
    Member findMember = memberRepository.findById(expectedId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // then
    assertNotNull(findMember);
    assertNotNull(findMember.getId());
    assertNotEquals(0L, findMember.getId());
    assertEquals(expectedId, findMember.getId());

    assertNotNull(findMember.getNickname());
    assertEquals(expectedNickname, findMember.getNickname());

    assertNull(findMember.getProfileImageUrl());

    assertNotNull(findMember.getParticipationMissions());
    assertEquals(expectedParticipationMissions, findMember.getParticipationMissions());
    assertEquals(0, findMember.getParticipationMissions().size());

    assertNotNull(findMember.getTopicOfInterests());
    assertEquals(2, findMember.getTopicOfInterests().size());
    assertEquals(expectedNameOfTopicOfInterest, findMember.getTopicOfInterests()
      .stream()
      .map(MemberOfTopicInterest::getTopicOfInterest)
      .map(TopicOfInterest::getName)
      .collect(Collectors.joining(","))
    );

    assertNotNull(findMember.getGrade());
    assertEquals(GradeStaus.BEGINNER, findMember.getGrade().getGradeStaus());

    assertTrue(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());
  }

  @Test
  @DisplayName("회원조회_관심항목없음")
  void findMemberNotInterestedEverything() {
    // given
    final long expectedId = 5L;
    final String expectedNickname = "bbubbush";
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();
    long expectedHasPoint = 0L;

    // when
    Member findMember = memberRepository.findById(expectedId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // then
    assertNotNull(findMember);
    assertNotNull(findMember.getId());
    assertNotEquals(0L, findMember.getId());
    assertEquals(expectedId, findMember.getId());

    assertNotNull(findMember.getNickname());
    assertEquals(expectedNickname, findMember.getNickname());

    assertNull(findMember.getProfileImageUrl());

    assertNotNull(findMember.getParticipationMissions());
    assertEquals(expectedParticipationMissions, findMember.getParticipationMissions());
    assertEquals(0, findMember.getParticipationMissions().size());

    assertNotNull(findMember.getTopicOfInterests());
    assertEquals(0, findMember.getTopicOfInterests().size());

    assertNotNull(findMember.getGrade());
    assertEquals(GradeStaus.BEGINNER, findMember.getGrade().getGradeStaus());
    assertEquals(expectedHasPoint, findMember.getGrade().getPoint());

    assertTrue(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());
  }

  @Test
  @DisplayName("회원등록")
  void createMemberBasic() {
    // given
    final String expectedNickname = "bbubbush";
    final String expectedEmail = "bbubbush@gmail.com";
    final String expectedProfileImageUrl = "/image/bbubbush/profile.png";
    final boolean expectedEmailAuthenticate = false;
    final boolean expectedTopicOfInterestAlarm = true;
    final boolean expectedWithdrawal = false;
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();
    final List<MemberOfTopicInterest> expectedTopicOfInterests = new ArrayList(){};
    Grade createGrade = Grade.builder()
      .gradeStaus(GradeStaus.BEGINNER)
      .build();

    Member createMember = Member.builder()
      .email(expectedEmail)
      .nickname(expectedNickname)
      .profileImageUrl(expectedProfileImageUrl)
      .isEmailAuthenticate(expectedEmailAuthenticate)
      .isTopicOfInterestAlarm(expectedTopicOfInterestAlarm)
      .isWithdrawal(expectedWithdrawal)
      .participationMissions(expectedParticipationMissions)
      .topicOfInterests(expectedTopicOfInterests)
      .grade(createGrade)
      .build();

    // when
    Member member = memberRepository.save(createMember);

    // then
    assertNotNull(member);
    assertNotNull(member.getId());
    assertNotEquals(0L, member.getId());

    assertNotNull(member.getNickname());
    assertEquals(expectedNickname, member.getNickname());

    assertNotNull(member.getProfileImageUrl());
    assertEquals(expectedProfileImageUrl, member.getProfileImageUrl());

    assertNotNull(member.getParticipationMissions());
    assertEquals(expectedParticipationMissions, member.getParticipationMissions());
    assertEquals(0, member.getParticipationMissions().size());

    assertNotNull(member.getTopicOfInterests());
    assertEquals(0, member.getTopicOfInterests().size());

    assertNotNull(member.getGrade());
    assertEquals(0L, member.getGrade().getPoint());
    assertEquals(GradeStaus.BEGINNER, member.getGrade().getGradeStaus());

    assertEquals(expectedTopicOfInterestAlarm, member.isTopicOfInterestAlarm());
    assertEquals(expectedEmailAuthenticate, member.isEmailAuthenticate());
    assertEquals(expectedWithdrawal, member.isWithdrawal());
  }

  @Test
  @DisplayName("회원정보변경")
  void updateMemberBasic() {
    // given
    final Long expectedMemberId = 7L;
    final String expectedNickname = "aauaaush";
    final String expectedEmail = "bbubbush@naver.com";
    final String expectedProfileImageUrl = "/image/bbubbush/profile123.png";
    final boolean expectedEmailAuthenticate = true;
    final boolean expectedTopicOfInterestAlarm = true;
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();

    Member findMember = memberRepository.findById(expectedMemberId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // when
    findMember.updateNickname(expectedNickname);
    findMember.updateEmail(expectedEmail);
    findMember.updateProfileImageUrl(expectedProfileImageUrl);
    findMember.updateIsEmailAuthenticate(expectedEmailAuthenticate);
    findMember.updateIsTopicOfInterestAlarm(expectedTopicOfInterestAlarm);

    // then
    assertNotNull(findMember);
    assertNotNull(findMember.getId());
    assertNotEquals(0L, findMember.getId());

    assertNotNull(findMember.getNickname());
    assertEquals(expectedNickname, findMember.getNickname());

    assertNotNull(findMember.getProfileImageUrl());
    assertEquals(expectedProfileImageUrl, findMember.getProfileImageUrl());

    assertNotNull(findMember.getParticipationMissions());
    assertEquals(expectedParticipationMissions, findMember.getParticipationMissions());
    assertEquals(0, findMember.getParticipationMissions().size());

    assertNotNull(findMember.getTopicOfInterests());
    assertEquals(2, findMember.getTopicOfInterests().size());

    assertNotNull(findMember.getGrade());
    assertEquals(0L, findMember.getGrade().getPoint());
    assertEquals(GradeStaus.BEGINNER, findMember.getGrade().getGradeStaus());

    assertEquals(expectedTopicOfInterestAlarm, findMember.isTopicOfInterestAlarm());
    assertEquals(expectedEmailAuthenticate, findMember.isEmailAuthenticate());
  }

  @Test
  @DisplayName("회원조회_이메일")
  void findMemberByEmail() {
    // given
    final long expectedId = 5L;
    final String expectedEmail = "bbubbush1@gmail.com";
    final String expectedNickname = "bbubbush";
    final String expectedNameOfTopicOfInterest = "";
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();

    // when
    Member findMember = memberRepository.findByEmail(expectedEmail)
      .orElseThrow(RuntimeException::new);

    // then
    assertNotNull(findMember);
    assertNotNull(findMember.getId());
    assertNotEquals(0L, findMember.getId());
    assertEquals(expectedId, findMember.getId());

    assertNotNull(findMember.getNickname());
    assertEquals(expectedNickname, findMember.getNickname());

    assertNull(findMember.getProfileImageUrl());

    assertNotNull(findMember.getParticipationMissions());
    assertEquals(expectedParticipationMissions, findMember.getParticipationMissions());
    assertEquals(0, findMember.getParticipationMissions().size());

    assertNotNull(findMember.getTopicOfInterests());
    assertEquals(0, findMember.getTopicOfInterests().size());
    assertEquals(expectedNameOfTopicOfInterest, findMember.getTopicOfInterests()
      .stream()
      .map(MemberOfTopicInterest::getTopicOfInterest)
      .map(TopicOfInterest::getName)
      .collect(Collectors.joining(","))
    );

    assertNotNull(findMember.getGrade());
    assertEquals(GradeStaus.BEGINNER, findMember.getGrade().getGradeStaus());

    assertTrue(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());
  }

}