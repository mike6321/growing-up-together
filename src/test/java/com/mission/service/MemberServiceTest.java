package com.mission.service;

import com.mission.domain.Member;
import com.mission.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

}