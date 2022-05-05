package com.mission.repository;

import com.mission.domain.TopicOfInterest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicOfInterestRepository extends CrudRepository<TopicOfInterest, Long> {

  TopicOfInterest findByName(String name);

}

