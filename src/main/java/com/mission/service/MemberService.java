package com.mission.service;

import com.mission.domain.Member;
import com.mission.domain.MemberOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.member.ReqCreateMember;
import com.mission.dto.member.ReqUpdateMember;
import com.mission.dto.member.ResFindMember;
import com.mission.dto.member.ResModifyMember;
import com.mission.repository.MemberOfTopicOfInterestRepository;
import com.mission.repository.MemberRepository;
import com.mission.repository.TopicOfInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

  @Value("{test.why}")
  private String why;

  private final MemberRepository memberRepository;
  private final MemberOfTopicOfInterestRepository memberOfTopicOfInterestRepository;
  private final TopicOfInterestRepository topicOfInterestRepository;
  private final PasswordEncoder passwordEncoder;

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
    memberCreateVO.encodePassword(passwordEncoder.encode(memberCreateVO.getPassword()));
    List<MemberOfTopicInterest> topicOfInterests = createMemberOfTopics(memberCreateVO.getTopicOfInterests());
    Member createMember = memberRepository.save(Member.createMember(memberCreateVO, topicOfInterests));
    return ResModifyMember.of(createMember.getId());
  }

  public ResModifyMember updateMember(ReqUpdateMember memberUpdateVO) {
    if (!isDuplicateEmail(memberUpdateVO.getEmail())) {
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
    boolean present = memberRepository.findByEmail(email).isPresent();
    return present;
  }

  private List<MemberOfTopicInterest> createMemberOfTopics(List<String> topicOfInterests) {
    final List<TopicOfInterest> existTopics = topicOfInterestRepository.findByNameIn(topicOfInterests);

    final List<String> existTopicNames = existTopics
      .stream()
      .map(TopicOfInterest::getName)
      .collect(Collectors.toList());

    final List<TopicOfInterest> nonExistsTopic = TopicOfInterest.nonExistsTopic(topicOfInterests, existTopicNames);

    return Stream.concat(
      transferMemberOfTopicStream(existTopics.stream()),
      transferMemberOfTopicStream(nonExistsTopic.stream())
    ).collect(Collectors.toList());
  }

  private Stream<MemberOfTopicInterest> transferMemberOfTopicStream(Stream<TopicOfInterest> stream) {
    return stream
      .map(topicOfInterest -> MemberOfTopicInterest
        .builder()
        .topicOfInterest(topicOfInterest)
        .build());
  }

  private void updateMemberOfTopics(Member findMember, List<String> updateTopics) {
    List<MemberOfTopicInterest> topicOfInterests = createMemberOfTopics(updateTopics);

    memberOfTopicOfInterestRepository.deleteByMember(findMember);
    findMember.deleteTopicOfInterests();

    topicOfInterests.forEach(findMember::addTopicOfInterests);
  }

}

/**
 *  master -> dev -> feature/security-jw
 *                -> release/security   ->    feature/security-sh
 *
 * */
