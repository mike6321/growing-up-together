package com.mission.service;

import com.mission.domain.*;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
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
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  private TopicOfInterestRepository topicOfInterestRepository;

  @Test
  @DisplayName("회원전체조회")
  void findAllMember() {
    // given

    // when
    final Iterable<Member> findMembers = memberService.findAll();

    // then
    assertThat(findMembers).isNotNull();
  }

  @Test
  @DisplayName("회원조회_기본")
  public void findMember() {
    // given
    final Member createMember = createMemberInDb();

    // when
    when(memberRepository.findById(createMember.getId())).thenReturn(Optional.of(createMember));
    final Member findMember = memberService.findById(createMember.getId());

    // then
    assertThat(findMember).isNotNull();
    assertThat(findMember.getId()).isNotNull()
      .isNotEqualTo(0L)
      .isGreaterThan(0L);
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
    assertThat(findMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.BEGINNER);
    assertThat(findMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(findMember.isTopicOfInterestAlarm()).isFalse();
    assertThat(findMember.isEmailAuthenticate()).isFalse();
    assertThat(findMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원등록_성공_회원관심항목없음")
  public void createMemberSuccess1() {
    // given
    final String expectedMemberEmail = "bbubbush99@gmail.com";
    final MemberCreateVO createVO = createMemberOfNothingInterestVO();

    // when
    when(memberRepository.findByEmail(expectedMemberEmail)).thenReturn(Optional.empty());
    when(memberRepository.save(any())).thenReturn(createMemberNothingInterest());
    final Member createMember = memberService.createMember(createVO);

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
    assertThat(createMember.getTopicOfInterests().size()).isEqualTo(0L);
    assertThat(createMember.getGrade()).isNotNull();
    assertThat(createMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.BEGINNER);
    assertThat(createMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(createMember.isTopicOfInterestAlarm()).isFalse();
    assertThat(createMember.isEmailAuthenticate()).isFalse();
    assertThat(createMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원등록_성공_회원관심항목있음")
  public void createMemberSuccess2() {
    // given
    final String expectedMemberEmail = "bbubbush99@gmail.com";
    final List<String> createUserTopic = new ArrayList<>(){{
      add("Spring");
      add("Java");
    }};
    final MemberCreateVO createVO = createMemberOfInterestVO(createUserTopic);
    final List<MemberOfTopicInterest> expectedMemberTopics = createMemberOfInterestOfTopic();

    // when
    when(memberRepository.findByEmail(expectedMemberEmail)).thenReturn(Optional.empty());
    when(memberRepository.save(any())).thenReturn(createMemberSpringAndJavaInterest());
    Member createMember = memberService.createMember(createVO);

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
    IntStream.range(0, createMember.getTopicOfInterests().size())
      .forEach(i -> assertThat(createMember.getTopicOfInterests().get(i).getTopicOfInterest().getName()).
        isEqualTo(expectedMemberTopics.get(i).getTopicOfInterest().getName()));
    assertThat(createMember.getGrade()).isNotNull();
    assertThat(createMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.BEGINNER);
    assertThat(createMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(createMember.isTopicOfInterestAlarm()).isFalse();
    assertThat(createMember.isEmailAuthenticate()).isFalse();
    assertThat(createMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원등록_실패_이메일중복")
  public void createMemberFail1() {
    // given
    final String expectedMessage = "중복된 이메일 입니다.";
    final Member findMember = createMemberInDb();
    final MemberCreateVO createVO = createMemberOfNothingInterestVO();

    // when
    when(memberRepository.findByEmail(createVO.getEmail())).thenReturn(Optional.of(findMember));
    RuntimeException duplicateEmailException = assertThrows(RuntimeException.class, () -> memberService.createMember(createVO));

    // then
    assertThat(duplicateEmailException.getMessage()).isEqualTo(expectedMessage);
  }

  @Test
  @DisplayName("회원정보변경_성공_회원관심항목없음")
  public void updateMemberSuccess1() {
    // given
    final Member findMember = createMemberInDb();
    final MemberUpdateVO updateVO = createUpdateMemberVO();

    // when
    when(memberRepository.findByEmail(findMember.getEmail())).thenReturn(Optional.empty());
    when(memberRepository.findById(updateVO.getId())).thenReturn(Optional.of(findMember));

    final Member updateMember = memberService.updateMember(updateVO);

    // then
    assertThat(updateMember).isNotNull();
    assertThat(updateMember.getId()).isNotNull()
      .isNotEqualTo(0L)
      .isGreaterThan(0L);
    assertThat(updateMember.getNickname()).isNotNull()
      .isNotEmpty();
    assertThat(updateMember.getEmail()).isNotNull()
      .isNotEmpty();
    assertThat(updateMember.getProfileImageUrl()).isNull();
    assertThat(updateMember.getParticipationMissions()).isNotNull();
    assertThat(updateMember.getParticipationMissions().size()).isEqualTo(0L);
    assertThat(updateMember.getTopicOfInterests()).isNotNull();
    assertThat(updateMember.getTopicOfInterests().size()).isEqualTo(0L);
    assertThat(updateMember.getGrade()).isNotNull();
    assertThat(updateMember.getGrade().getGradeStaus()).isEqualTo(GradeStaus.BEGINNER);
    assertThat(updateMember.getGrade().getPoint()).isEqualTo(0L);
    assertThat(updateMember.isTopicOfInterestAlarm()).isFalse();
    assertThat(updateMember.isEmailAuthenticate()).isFalse();
    assertThat(updateMember.isWithdrawal()).isFalse();
  }

  @Test
  @DisplayName("회원정보변경_실패_회원조회실패")
  public void updateMemberFail1() {
    // given
    final String expectedMessage = "일치하는 회원을 찾을 수 없습니다.";
    final MemberUpdateVO updateVO = createUpdateMemberVO();

    // when
    RuntimeException memberNotFoundException = assertThrows(RuntimeException.class, () -> memberService.updateMember(updateVO));

    // then
    assertThat(memberNotFoundException.getMessage()).isEqualTo(expectedMessage);
  }

  @Test
  @DisplayName("회원정보변경_실패_이메일중복")
  public void updateMemberFail2() {
    // given
    final String expectedMessage = "중복된 이메일 입니다.";
    final MemberUpdateVO updateVO = createUpdateMemberVO();

    // when
    when(memberRepository.findByEmail(updateVO.getEmail())).thenReturn(Optional.of(createMemberNothingInterest()));
    RuntimeException duplicateEmailException = assertThrows(RuntimeException.class, () -> memberService.updateMember(updateVO));

    // then
    assertThat(duplicateEmailException.getMessage()).isEqualTo(expectedMessage);
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

  private Member createMemberSpringAndJavaInterest() {
    return Member.builder()
      .id(99L)
      .nickname("bbubbush99")
      .profileImageUrl(null)
      .email("bbubbush99@gmail.com")
      .participationMissions(new ArrayList<>())
      .topicOfInterests(createMemberOfInterestOfTopic())
      .grade(createBeginnerGrade())
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();
  }

  private List<MemberOfTopicInterest> createMemberOfInterestOfTopic() {
    List<MemberOfTopicInterest> topicOfInterests = new ArrayList<>();
    topicOfInterests.add(MemberOfTopicInterest.builder()
      .memberOfTopicOfInterestId(1L)
      .topicOfInterest(TopicOfInterest.builder()
        .topicOfInterestId(0L)
        .name("Spring")
        .build())
      .build());
    topicOfInterests.add(MemberOfTopicInterest.builder()
      .memberOfTopicOfInterestId(2L)
      .topicOfInterest(TopicOfInterest.builder()
        .topicOfInterestId(1L)
        .name("Java")
        .build())
      .build());
    return topicOfInterests;
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

  private Member createMemberInDb() {
    return Member.builder()
      .id(5L)
      .nickname("bbubbush")
      .profileImageUrl(null)
      .email("bbubbush1@gmail.com")
      .participationMissions(new ArrayList<>())
      .topicOfInterests(new ArrayList<>())
      .grade(Grade.builder().id(99L).gradeStaus(GradeStaus.BEGINNER).build())
      .isTopicOfInterestAlarm(false)
      .isEmailAuthenticate(false)
      .isWithdrawal(false)
      .build();
  }

  private MemberUpdateVO createUpdateMemberVO() {
    return new MemberUpdateVO(99L, "bbubbush", false, null, false, false, createBeginnerGrade(), "bbubbush1@gmail.com", new ArrayList<>());
  }

}