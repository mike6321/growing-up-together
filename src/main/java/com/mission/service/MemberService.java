package com.mission.service;

import com.mission.domain.Grade;
import com.mission.domain.GradeStaus;
import com.mission.domain.Member;
import com.mission.repository.GradeRepository;
import com.mission.repository.MemberRepository;
import com.mission.vo.MemberCreateVO;
import com.mission.vo.MemberUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

  private final MemberRepository memberRepository;
  private final GradeRepository gradeRepository;

  public Member findById(Long memberId) {
    return memberRepository.findById(memberId)
      .orElse(null);
  }

  public Iterable<Member> findById() {
    return memberRepository.findAll();
  }

  public Member createMember(MemberCreateVO memberCreateVO) {
    if (!memberRepository.findByEmail(memberCreateVO.getEmail()).isEmpty()) {
      // TODO 예외 생성 및 에러코드 정의 필요
      throw new RuntimeException("중복된 이메일 입니다.");
    }

    Grade grade = Grade.builder()
      .point(0L)
      .gradeStaus(GradeStaus.BEGINNER)
      .build();
    Grade findGrade = gradeRepository.save(grade);

    Member createMember = Member.builder()
      .email(memberCreateVO.getEmail())
      .nickname(memberCreateVO.getNickname())
      .isTopicOfInterestAlarm(memberCreateVO.isTopicOfInterestAlarm())
      .grade(findGrade)
      .build();
    return memberRepository.save(createMember);
  }

  public Member updateMember(MemberUpdateVO memberUpdateVO) {
    Member findMember = findById(memberUpdateVO.getId());
//    findMember

    return memberRepository.save(findMember);
  }

}
