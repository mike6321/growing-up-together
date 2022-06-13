package com.mission.domain;

import com.provider.mission.MissionProvider;
import com.provider.mission.TopicOfInterestProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionOfTopicInterestTest {

    @DisplayName("미션 생성 테스트")
    @ParameterizedTest
    @MethodSource(MissionProvider.PROVIDER_CLASSPATH + "missionProvider")
    void create_mission_test(Mission mission) {
        MissionOfTopicInterest missionOfTopicInterest = new MissionOfTopicInterest();
        missionOfTopicInterest.createMission(mission);
        assertThat(missionOfTopicInterest.getMission()).isEqualTo(mission);
    }

    @DisplayName("타입 변환 테스트 (List<TopicOfInterest> -> List<MissionOfTopicInterest>)")
    @ParameterizedTest
    @MethodSource(TopicOfInterestProvider.PROVIDER_CLASSPATH + "topicOfInterestListProvider")
    void mission_of_test(List<TopicOfInterest> topicOfInterests) {
        List<MissionOfTopicInterest> missionOfTopicInterests = MissionOfTopicInterest.of(topicOfInterests);
        IntStream.range(0, topicOfInterests.size())
                .forEach(index -> assertThat(missionOfTopicInterests.get(index).getTopicOfInterest())
                        .isEqualTo(topicOfInterests.get(index)));
    }

}
