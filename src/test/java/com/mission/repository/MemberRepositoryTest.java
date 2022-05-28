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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

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
    final Member createMember = memberRepository.save(createMemberSpringAndJavaInterest());
    final Long expectedId = createMember.getId();

    // when
    final Member findMember = memberRepository.findById(expectedId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // then
    assertThat(findMember).isNotNull();
    assertThat(findMember.getId()).isNotNull()
      .isGreaterThan(0L)
      .isEqualTo(expectedId);
    assertThat(findMember.getNickname()).isNotNull()
      .isNotEmpty();
    assertThat(findMember.getEmail()).isNotNull()
      .isNotEmpty();
    assertThat(findMember.getProfileImageUrl()).isNull();
    assertThat(findMember.getParticipationMissions()).isNotNull();
    assertThat(findMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(findMember.getTopicOfInterests()).isNotNull();
    assertThat(findMember.getTopicOfInterests().size()).isEqualTo(2L);
    assertThat(findMember.getGrade()).isNotNull();
    assertThat(findMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.ROLE_BEGINNER);
    assertThat(findMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(findMember.isTopicOfInterestAlarm()).isTrue();
    assertThat(findMember.isEmailAuthenticate()).isFalse();
    assertThat(findMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원조회_관심항목없음")
  void findMemberNotInterestedEverything() {
    // given
    final Member createMember = memberRepository.save(createMemberNothingInterest());
    final long expectedId = createMember.getId();

    // when
    final Member findMember = memberRepository.findById(expectedId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // then
    assertThat(findMember).isNotNull();
    assertThat(findMember.getId()).isNotNull()
      .isGreaterThan(0L)
      .isEqualTo(expectedId);
    assertThat(findMember.getNickname()).isNotNull()
      .isNotEmpty();
    assertThat(findMember.getEmail()).isNotNull()
      .isNotEmpty();
    assertThat(findMember.getProfileImageUrl()).isNull();
    assertThat(findMember.getParticipationMissions()).isNotNull();
    assertThat(findMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(findMember.getTopicOfInterests()).isNotNull();
    assertThat(findMember.getTopicOfInterests().size()).isEqualTo(0L);
    assertThat(findMember.getGrade()).isNotNull();
    assertThat(findMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.ROLE_BEGINNER);
    assertThat(findMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(findMember.isTopicOfInterestAlarm()).isTrue();
    assertThat(findMember.isEmailAuthenticate()).isFalse();
    assertThat(findMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원등록")
  void createMemberBasic() {
    // given

    // when
    final Member createMember = memberRepository.save(createMemberSpringAndJavaInterest());

    // then
    assertThat(createMember).isNotNull();
    assertThat(createMember.getId()).isNotNull()
      .isNotEqualTo(0L)
      .isGreaterThan(0L);
    assertThat(createMember.getNickname()).isNotNull()
      .isNotEmpty();
    assertThat(createMember.getEmail()).isNotNull()
      .isNotEmpty();
    assertThat(createMember.getProfileImageUrl()).isNull();
    assertThat(createMember.getParticipationMissions()).isNotNull();
    assertThat(createMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(createMember.getTopicOfInterests()).isNotNull();
    assertThat(createMember.getTopicOfInterests().size()).isEqualTo(2L);
    assertThat(createMember.getGrade()).isNotNull();
    assertThat(createMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(createMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.ROLE_BEGINNER);
    assertThat(createMember.isTopicOfInterestAlarm()).isTrue();
    assertThat(createMember.isEmailAuthenticate()).isFalse();
    assertThat(createMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원정보변경")
  void updateMemberBasic() {
    // given
    final Member createMember = memberRepository.save(createMemberNothingInterest());
    final Long targetMemId = createMember.getId();

    final String updateNewNickname = "bbubbush001";
    final String updateNewEmail = "bbubbush@naver.com";
    final String expectedProfileImageUrl = "/image/bbubbush/profile123.png";
    final boolean updateNewEmailAuthenticate = true;
    final boolean updateNewTopicOfInterestAlarm = true;
    final List<MemberOfTopicInterest> expectedMemberTopics = createMemberOfInterestOfTopic();

    final Member findMember = memberRepository.findById(targetMemId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));

    // when
    findMember.updateNickname(updateNewNickname);
    findMember.updateEmail(updateNewEmail);
    findMember.updateProfileImageUrl(expectedProfileImageUrl);
    findMember.updateIsEmailAuthenticate(updateNewEmailAuthenticate);
    findMember.updateIsTopicOfInterestAlarm(updateNewTopicOfInterestAlarm);
    createMemberOfInterestOfTopic().forEach(findMember::addTopicOfInterests);

    // then
    assertThat(findMember).isNotNull();
    assertThat(findMember.getId()).isNotEqualTo(0L);
    assertThat(findMember.getId()).isGreaterThan(0L);
    assertThat(findMember.getNickname()).isNotNull();
    assertThat(findMember.getNickname()).isNotEmpty();
    assertThat(findMember.getNickname()).isEqualTo(updateNewNickname);
    assertThat(findMember.getEmail()).isNotNull();
    assertThat(findMember.getEmail()).isNotEmpty();
    assertThat(findMember.getEmail()).isEqualTo(updateNewEmail);
    assertThat(findMember.getProfileImageUrl()).isNotNull();
    assertThat(findMember.getProfileImageUrl()).isNotEmpty();
    assertThat(findMember.getProfileImageUrl()).isEqualTo(expectedProfileImageUrl);
    assertThat(findMember.getParticipationMissions()).isNotNull();
    assertThat(findMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(findMember.getTopicOfInterests()).isNotNull();
    assertThat(findMember.getTopicOfInterests().size()).isEqualTo(2L);
    IntStream.range(0, findMember.getTopicOfInterests().size())
      .forEach(i -> assertThat(findMember.getTopicOfInterests().get(i).getTopicOfInterest().getName()).
        isEqualTo(expectedMemberTopics.get(i).getTopicOfInterest().getName()));
    assertThat(findMember.getGrade()).isNotNull();
    assertThat(findMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(findMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.ROLE_BEGINNER);
    assertThat(findMember.isTopicOfInterestAlarm()).isTrue();
    assertThat(findMember.isEmailAuthenticate()).isTrue();
    assertThat(findMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원조회_이메일")
  void findMemberByEmail() {
    // given
    final Member createMember = memberRepository.save(createMemberNothingInterest());

    // when
    final Member findMember = memberRepository.findByEmail(createMember.getEmail())
      .orElseThrow(RuntimeException::new);

    // then
    assertThat(findMember).isNotNull();
    assertThat(findMember.getId()).isNotEqualTo(0L);
    assertThat(findMember.getId()).isGreaterThan(0L);
    assertThat(findMember.getNickname()).isNotNull();
    assertThat(findMember.getNickname()).isNotEmpty();
    assertThat(findMember.getProfileImageUrl()).isNull();
    assertThat(findMember.getParticipationMissions()).isNotNull();
    assertThat(findMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(findMember.getTopicOfInterests()).isNotNull();
    assertThat(findMember.getTopicOfInterests().size()).isEqualTo(0L);
    assertThat(findMember.getGrade()).isNotNull();
    assertThat(findMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(findMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.ROLE_BEGINNER);
    assertThat(findMember.isTopicOfInterestAlarm()).isTrue();
    assertThat(findMember.isEmailAuthenticate()).isFalse();
    assertThat(findMember.isWithdrawal()).isFalse();
  }

  private Member createMemberSpringAndJavaInterest() {
    return Member.builder()
      .email("bbubbush99@gmail.com")
      .nickname("bbubbush99")
      .isEmailAuthenticate(false)
      .isTopicOfInterestAlarm(true)
      .isWithdrawal(false)
      .participationMissions(new ArrayList<>())
      .topicOfInterests(createMemberOfInterestOfTopic())
      .grade(createROLE_BEGINNERGrade())
      .build();
  }

  private Member createMemberNothingInterest() {
    return Member.builder()
      .email("bbubbush99@gmail.com")
      .nickname("bbubbush99")
      .isEmailAuthenticate(false)
      .isTopicOfInterestAlarm(true)
      .isWithdrawal(false)
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(createROLE_BEGINNERGrade())
      .build();
  }

  private Grade createROLE_BEGINNERGrade() {
    return Grade.builder()
      .gradeStaus(GradeStaus.ROLE_BEGINNER)
      .build();
  }

  private List<MemberOfTopicInterest> createMemberOfInterestOfTopic() {
    List<MemberOfTopicInterest> topicOfInterests = new ArrayList<>();
    topicOfInterests.add(MemberOfTopicInterest.builder()
      .topicOfInterest(TopicOfInterest.builder()
        .name("Spring")
        .build())
      .build());
    topicOfInterests.add(MemberOfTopicInterest.builder()
      .topicOfInterest(TopicOfInterest.builder()
        .name("Java")
        .build())
      .build());
    return topicOfInterests;
  }

}
