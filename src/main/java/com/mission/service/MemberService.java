package com.mission.service;

import com.mission.domain.Grade;
import com.mission.domain.Member;
import com.mission.domain.MemberOfTopicInterest;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final TopicOfInterestRepository topicOfInterestRepository;

  public Member findById(Long memberId) {
    return memberRepository.findById(memberId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));  // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
  }

  public Iterable<Member> findAll() {
    return memberRepository.findAll();

  }

  public Member createMember(MemberCreateVO memberCreateVO) {
    if (isDuplicateEmail(memberCreateVO.getEmail())) {
      // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    Grade createGrade = Grade.createBeginnerGrade();

    List<MemberOfTopicInterest> topicOfInterests = createMemberOfTopics(memberCreateVO.getTopicOfInterests());

    Member createMember = memberRepository.save(Member.createMember(memberCreateVO, createGrade));

    topicOfInterests.forEach(createMember::addTopicOfInterests);
    return createMember;

  }

  public Member updateMember(MemberUpdateVO memberUpdateVO) {
    if (isDuplicateEmail(memberUpdateVO.getEmail())) {
      // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    Member findMember = findById(memberUpdateVO.getId());
    findMember.updateEmail(memberUpdateVO.getEmail());
    findMember.updateNickname(memberUpdateVO.getNickname());
    findMember.updateIsWithdrawal(memberUpdateVO.isWithdrawal());
    findMember.updateIsTopicOfInterestAlarm(memberUpdateVO.isTopicOfInterestAlarm());
    updateMemberOfTopics(findMember, memberUpdateVO.getTopicOfInterests());

    return findMember;

  }

  private boolean isDuplicateEmail(String email) {
    return memberRepository.findByEmail(email).isPresent();

  }

  private List<MemberOfTopicInterest> createMemberOfTopics(List<String> topicOfInterests) {
    return topicOfInterests
      .stream()
      .map(topicOfInterestRepository::findByName)
      .filter(Objects::nonNull)
      .map(topicOfInterest -> MemberOfTopicInterest.builder()
        .topicOfInterest(topicOfInterest)
        .build())
      .collect(Collectors.toList());

  }

  private void updateMemberOfTopics(Member findMember, List<String> updateTopics) {
    List<MemberOfTopicInterest> topicOfInterests = createMemberOfTopics(updateTopics);
    findMember.getTopicOfInterests().clear();
    topicOfInterests.forEach(findMember::addTopicOfInterests);

  }
}
