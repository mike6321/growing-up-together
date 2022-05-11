package com.mission.repository;

import com.mission.domain.TopicOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TopicOfInterestRepository extends JpaRepository<TopicOfInterest, Long> {

    List<TopicOfInterest> findByNameIn(List<String> topicInterestsNames);
    TopicOfInterest findByName(String name);

}
