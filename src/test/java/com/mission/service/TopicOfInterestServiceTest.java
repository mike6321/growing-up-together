package com.mission.service;

import com.mission.domain.Holiday;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.RequestCreateMission;
import com.mission.repository.TopicOfInterestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TopicOfInterestServiceTest {

    @Autowired private TopicOfInterestService topicOfInterestService;
    @Autowired private TopicOfInterestRepository topicOfInterestRepository;

    private List<String> missionOfTopicInterestsNames;
    private RequestCreateMission requestCreateMission;

    @BeforeEach
    void initRequestData() {
        missionOfTopicInterestsNames = List.of("Spring", "Devops");
        Holiday holiday = new Holiday(true, false, false, true, true, true, true);
        requestCreateMission = RequestCreateMission.builder()
                .subject("미션1")
                .holiday(holiday)
                .numberOfParticipants(3)
                .creator("junwoo.choi")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .missionOfTopicInterests(missionOfTopicInterestsNames)
                .build();
    }

    @DisplayName("관심주제 생성 테스트")
    @Test
    public void create_mission_of_topic_interests_test() throws Exception {
        // when
        topicOfInterestService.getMissionOfTopicInterests(requestCreateMission);
        // then
        List<TopicOfInterest> topicOfInterests = topicOfInterestRepository.findByNameIn(missionOfTopicInterestsNames);
        assertThat(topicOfInterests.size()).isEqualTo(2);
        IntStream.range(0, topicOfInterests.size())
                .forEach(i -> assertThat(topicOfInterests.get(i).getName()).isEqualTo(missionOfTopicInterestsNames.get(i)));
    }

}
