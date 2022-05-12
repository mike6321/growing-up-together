package com.mission.repository;

import com.mission.domain.Member;
import com.mission.domain.MemberOfTopicInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOfTopicOfInterestRepository extends JpaRepository<MemberOfTopicInterest, Long> {

  int deleteByMember(Member member);

}
