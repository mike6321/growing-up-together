package com.mission.service;

import com.mission.domain.TopicOfInterest;
import com.mission.repository.TopicOfInterestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MissionServiceTest {

    @Autowired
    private MissionService missionService;
    @Autowired
    private TopicOfInterestRepository topicOfInterestRepository;

    @Test
    @Transactional
    @DisplayName("관심주제 생성 테스트")
    public void create_mission_of_topic_interest() throws Exception {
        // given
        List<String> topicOfInterestNames = List.of("Spring", "React");
        missionService.createMissionOfTopicInterests(topicOfInterestNames);
        // when
        List<TopicOfInterest> topicOfInterests = topicOfInterestRepository.findByNameIn(topicOfInterestNames);
        // then
        assertThat(topicOfInterests.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("관심주제 중복 생성 테스트")
    public void create_duplicate_mission_of_topic_interest() throws Exception {
        // given
        List<String> topicOfInterestNames = List.of("Spring", "React", "Spring");
        missionService.createMissionOfTopicInterests(topicOfInterestNames);
        // when
        List<TopicOfInterest> topicOfInterests = topicOfInterestRepository.findByNameIn(topicOfInterestNames);
        // then
        for (TopicOfInterest topicOfInterest : topicOfInterests) {
            System.out.println("*****************************");
            System.out.println(topicOfInterest.getTopicOfInterestId());
            System.out.println(topicOfInterest.getName());
            System.out.println("*****************************");
        }
        assertThat(topicOfInterests.size()).isEqualTo(2);
    }

}
