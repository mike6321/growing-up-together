package com.mission.repository;

import com.mission.domain.TopicOfInterest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TopicOfInterestRepository extends CrudRepository<TopicOfInterest, Long> {

  TopicOfInterest findByName(String name);

}
