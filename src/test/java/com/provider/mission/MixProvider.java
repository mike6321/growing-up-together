package com.provider.mission;

import com.mission.domain.Holiday;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.ReqCreateMission;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class MixProvider {

    public static final String PROVIDER_CLASSPATH = "com.provider.mission.MixProvider#";

    static Stream<Arguments> reqCreateMissionAndMissionOfTopicInterestsProvider() {
        Holiday holiday = new Holiday(true, true, true, true, true, true, true);
        String subject = "subject";
        int numberOfParticipants = 3;
        String creator = "junwoo";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(7);
        List<String> topicOfInterestNames = List.of("spring", "kafka");
        List<TopicOfInterest> topicOfInterests = TopicOfInterest.nonExistsTopic(topicOfInterestNames, List.of("vue.js"));
        List<MissionOfTopicInterest> missionOfTopicInterests = MissionOfTopicInterest.of(topicOfInterests);
        return Stream.of(Arguments.arguments(
                Named.of("ReqCreateMission", ReqCreateMission.builder()
                        .subject(subject)
                        .holiday(holiday)
                        .numberOfParticipants(numberOfParticipants)
                        .creator(creator)
                        .startDate(startDate)
                        .endDate(endDate)
                        .missionOfTopicInterests(topicOfInterestNames)
                        .build()
                ),
                Named.of("List<MissionOfTopicInterest>", missionOfTopicInterests)
                )
        );
    }

}
