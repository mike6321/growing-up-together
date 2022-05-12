package com.mission.repository;

import com.mission.domain.MemberOfTopicInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOfTopicOfInterestRepository extends JpaRepository<MemberOfTopicInterest, Long> {}
