package com.mission.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionOfTopicInterestTest {

    @DisplayName("MissionOfTopicInterest - getter")
    @ParameterizedTest
    @MethodSource("missionOfTopicInterestTestProvider")
    void gettetTest(Long inputId,
                    TopicOfInterest inputTopicOfInterest,
                    Mission inputMission,
                    MissionOfTopicInterest missionOfTopicInterest) {
        assertThat(missionOfTopicInterest.getId()).isEqualTo(inputId);
        assertThat(missionOfTopicInterest.getTopicOfInterest()).isEqualTo(inputTopicOfInterest);
        assertThat(missionOfTopicInterest.getMission()).isEqualTo(inputMission);
    }

    @DisplayName("미션 생성 테스트")
    @ParameterizedTest
    @MethodSource("missionProvider")
    void create_mission_test(Mission mission) {
        MissionOfTopicInterest missionOfTopicInterest = new MissionOfTopicInterest();
        missionOfTopicInterest.createMission(mission);
        assertThat(missionOfTopicInterest.getMission()).isEqualTo(mission);
    }

    @DisplayName("타입 변환 테스트 (List<TopicOfInterest> -> List<MissionOfTopicInterest>)")
    @ParameterizedTest
    @MethodSource("topicOfInterestProvider")
    void mission_of_test(List<TopicOfInterest> topicOfInterests) {
        List<MissionOfTopicInterest> missionOfTopicInterests = MissionOfTopicInterest.of(topicOfInterests);
        IntStream.range(0, topicOfInterests.size())
                .forEach(index -> assertThat(missionOfTopicInterests.get(index).getTopicOfInterest())
                        .isEqualTo(topicOfInterests.get(index)));
    }

    static Stream<Arguments> missionOfTopicInterestTestProvider() {
        Long missionOfTopicInterestId = 1L;
        TopicOfInterest topicOfInterest = new TopicOfInterest();
        Mission mission = new Mission();
        return Stream.of(Arguments.arguments(
                        missionOfTopicInterestId, topicOfInterest, mission,
                        MissionOfTopicInterest.builder()
                                .id(missionOfTopicInterestId)
                                .topicOfInterest(topicOfInterest)
                                .mission(mission)
                                .build()
                )
        );
    }

    static Stream<Arguments> topicOfInterestProvider() {
        TopicOfInterest topicOfInterest1 = new TopicOfInterest();
        TopicOfInterest topicOfInterest2 = new TopicOfInterest();
        List<TopicOfInterest> topicOfInterests = List.of(topicOfInterest1, topicOfInterest2);
        return Stream.of(Arguments.arguments(topicOfInterests));
    }

    static Stream<Arguments> missionProvider() {
        Mission mission = new Mission();
        return Stream.of(Arguments.arguments(mission));
    }

}
