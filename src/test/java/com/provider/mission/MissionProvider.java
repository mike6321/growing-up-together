package com.provider.mission;

import com.mission.domain.Holiday;
import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.service.Utils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class MissionProvider {

    public static final String PROVIDER_CLASSPATH = "com.provider.mission.MissionProvider#";

    static Stream<Arguments> missionProvider() {
        MissionOfTopicInterest missionOfTopicInterest = MissionOfTopicInterest.builder()
                .topicOfInterest(new TopicOfInterest())
                .build();
        List<MissionOfTopicInterest> missionOfTopicInterests = List.of(missionOfTopicInterest);
        return Stream.of(Arguments.of(
                Mission
                        .builder()
                        .id(1L)
                        .subject("주제1")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(3)
                        .creator("test_account1@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

}
