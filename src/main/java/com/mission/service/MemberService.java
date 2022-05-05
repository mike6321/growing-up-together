package com.mission.service;

import com.mission.domain.*;
import com.mission.repository.GradeRepository;
import com.mission.repository.MemberOfTopicOfInterestRepository;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final GradeRepository gradeRepository;
  private final TopicOfInterestRepository topicOfInterestRepository;
  private final MemberOfTopicOfInterestRepository memberOfTopicOfInterestRepository;

  public Member findById(Long memberId) {
    Member findMember = memberRepository.findById(memberId)
      .orElse(null);
    return findMember;

  }

  public Iterable<Member> findAll() {
    return memberRepository.findAll();

  }

  public Member createMember(MemberCreateVO memberCreateVO) {
    if (isDuplicateEmail(memberCreateVO.getEmail())) {
      // TODO 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    // 회원등급 생성
    Grade grade = Grade.builder()
      .point(0L)
      .gradeStaus(GradeStaus.BEGINNER)
      .build();
    Grade createGrade = gradeRepository.save(grade);

    // 회원관심주제 생성
    List<MemberOfTopicInterest> topicOfInterests = memberCreateVO.getTopicOfInterests()
      .stream()
      .map(topic -> topicOfInterestRepository.findByName(topic))
      .map(topicOfInterest -> MemberOfTopicInterest.builder()
        .topicOfInterest(topicOfInterest)
        .build())
      .collect(Collectors.toList());

    // 회원 생성
    Member createMember = Member.builder()
      .email(memberCreateVO.getEmail())
      .nickname(memberCreateVO.getNickname())
      .isTopicOfInterestAlarm(memberCreateVO.isTopicOfInterestAlarm())
      .grade(createGrade)
      .build();
    memberRepository.save(createMember);

    // 회원 관심주제 변경
    topicOfInterests.forEach(topicOfInterest -> createMember.addTopicOfInterests(topicOfInterest));

    // 회원관심주제 등록
    topicOfInterests.forEach(topicOfInterest -> memberOfTopicOfInterestRepository.save(topicOfInterest));
    return createMember;

  }

  public Member updateMember(MemberUpdateVO memberUpdateVO) {
    Member findMember = findById(memberUpdateVO.getId());
    findMember.updateNickname(memberUpdateVO.getNickname());
    findMember.updateIsWithdrawal(memberUpdateVO.isWithdrawal());
    findMember.updateIsTopicOfInterestAlarm(memberUpdateVO.isTopicOfInterestAlarm());
    if (isDuplicateEmail(memberUpdateVO.getEmail())) {
      // TODO 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }
    findMember.updateEmail(memberUpdateVO.getEmail());
    // TODO 회원 관심주제 업데이트 로직 추가 예정
    return findMember;

  }

  private boolean isDuplicateEmail(String email) {
    return !memberRepository.findByEmail(email).isEmpty();

  }

}
