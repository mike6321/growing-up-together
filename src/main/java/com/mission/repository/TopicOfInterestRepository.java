package com.mission.repository;

import com.mission.domain.TopicOfInterest;
import com.mission.domain.TopicOfInterestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicOfInterestRepository extends JpaRepository<TopicOfInterest, TopicOfInterestId> {

    @Transactional(readOnly = true)
    List<TopicOfInterest> findByNameIn(List<String> topicInterestsNames);
    @Transactional(readOnly = true)
    TopicOfInterest findByName(String topicInterestsName);

}
