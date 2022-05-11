package com.mission.repository;

import com.mission.domain.MemberOfTopicInterest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MemberOfTopicOfInterestRepository extends CrudRepository<MemberOfTopicInterest, Long> {}
