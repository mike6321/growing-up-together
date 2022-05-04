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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("회원조회_기본")
  void findMemberBasic() {
    // given
    final long expectedId = 1L;
    final String expectedNickname = "bbubbush";
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();
    final List<MemberOfTopicInterest> expectedTopicOfInterests = new ArrayList();

    // when
    Member findMember = memberRepository.findById(expectedId)
      .orElse(null);

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
    assertEquals(expectedTopicOfInterests, findMember.getTopicOfInterests());
    assertEquals(0, findMember.getTopicOfInterests().size());

    assertNull(findMember.getGrade());
    assertFalse(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());

  }

  @Test
  @DisplayName("회원조회_Grade 추가")
  void findMemberAddGrade() {
    // given
    final long expectedId = 19L;
    final String expectedNickname = "bbubbush";
    final GradeStaus expectedGradeStatus = GradeStaus.BEGINNER;
    final List<ParticipationMission> expectedParticipationMissions = new ArrayList();
    final List<MemberOfTopicInterest> expectedTopicOfInterests = new ArrayList();

    // when
    Member findMember = memberRepository.findById(expectedId)
      .orElse(null);

    Grade findMemberGrade = findMember.getGrade();
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
    assertEquals(expectedTopicOfInterests, findMember.getTopicOfInterests());
    assertEquals(0, findMember.getTopicOfInterests().size());

//    assertNotNull(findMember.getGrade());
//    assertEquals(expectedGradeStatus, findMember.getGrade().getGradeStaus());

    assertTrue(findMember.isTopicOfInterestAlarm());
    assertFalse(findMember.isEmailAuthenticate());
    assertFalse(findMember.isWithdrawal());

  }

}