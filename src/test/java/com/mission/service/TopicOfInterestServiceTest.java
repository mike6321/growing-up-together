package com.mission.service;

import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.repository.MissionOfTopicInterestRepository;
import com.mission.repository.TopicOfInterestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TopicOfInterestServiceTest {

    @InjectMocks TopicOfInterestService topicOfInterestService;
    @Mock MissionOfTopicInterestRepository missionOfTopicInterestRepository;

    @DisplayName("관심주제 생성 테스트 - 이미 저장된 관심 주제 가져오기 테스트")
    @ParameterizedTest
    @MethodSource("missionOfTopicInterestsProvider")
    void get_mission_of_topic_interests(ReqCreateMission reqCreateMission) {
        // given
        when(missionOfTopicInterestRepository.saveAll(any(List.class))).thenReturn(any(List.class));
        // when
        List<MissionOfTopicInterest> missionOfTopicInterests = topicOfInterestService.getMissionOfTopicInterests(reqCreateMission);
        // then
        assertThat(missionOfTopicInterests.size()).isEqualTo(3);
    }

    static Stream<Arguments> missionOfTopicInterestsProvider() {
        return Stream.of(Arguments.arguments(
                ReqCreateMission.builder()
                        .missionOfTopicInterests(List.of("spring", "kafka", "reactive programming"))
                        .build()));
    }

}
