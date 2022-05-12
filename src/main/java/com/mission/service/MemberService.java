package com.mission.service;

import com.mission.domain.Grade;
import com.mission.domain.Member;
import com.mission.domain.MemberOfTopicInterest;
import com.mission.dto.member.ReqCreateMember;
import com.mission.dto.member.ReqUpdateMember;
import com.mission.dto.member.ResFindMember;
import com.mission.dto.member.ResModifyMember;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final TopicOfInterestRepository topicOfInterestRepository;

  @Transactional(readOnly = true)
  public ResFindMember findById(Long memberId) {
    return ResFindMember.of(getFindMember(memberId));
  }

  @Transactional(readOnly = true)
  public List<ResFindMember> findAll() {
    return memberRepository.findAll()
      .stream()
      .map(ResFindMember::of)
      .collect(Collectors.toList());
  }

  public ResModifyMember createMember(ReqCreateMember memberCreateVO) {
    if (isDuplicateEmail(memberCreateVO.getEmail())) {
      // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    Grade createGrade = Grade.createBeginnerGrade();

    List<MemberOfTopicInterest> topicOfInterests = createMemberOfTopics(memberCreateVO.getTopicOfInterests());

    Member createMember = memberRepository.save(Member.createMember(memberCreateVO, createGrade));

    topicOfInterests.forEach(createMember::addTopicOfInterests);
    return ResModifyMember.of(createMember.getId());
  }

  public ResModifyMember updateMember(ReqUpdateMember memberUpdateVO) {
    if (isDuplicateEmail(memberUpdateVO.getEmail())) {
      // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    Member findMember = getFindMember(memberUpdateVO.getId());
    findMember.updateEmail(memberUpdateVO.getEmail());
    findMember.updateNickname(memberUpdateVO.getNickname());
    findMember.updateIsTopicOfInterestAlarm(memberUpdateVO.isTopicOfInterestAlarm());
    updateMemberOfTopics(findMember, memberUpdateVO.getTopicOfInterests());

    return ResModifyMember.of(findMember.getId());
  }

  private Member getFindMember(Long memberId) {
    // TODO bbubbush :: 예외 생성 및 에러코드 정의 필요
    return memberRepository.findById(memberId)
      .orElseThrow(() -> new RuntimeException("일치하는 회원을 찾을 수 없습니다."));
  }

  private boolean isDuplicateEmail(String email) {
    return memberRepository.findByEmail(email).isPresent();
  }

  private List<MemberOfTopicInterest> createMemberOfTopics(List<String> topicOfInterests) {
    return topicOfInterests.stream()
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
