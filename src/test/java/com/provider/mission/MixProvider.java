package com.provider.mission;

import com.mission.domain.*;
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

    static Stream<Arguments> getterMissionOfTopicInterestProvider() {
        Long missionOfTopicInterestId = 1L;
        TopicOfInterest topicOfInterest = new TopicOfInterest();
        Mission mission = new Mission();
        return Stream.of(Arguments.arguments(
                        Named.of("MissionOfTopicInterestId", missionOfTopicInterestId),
                        Named.of("TopicOfInterest", topicOfInterest),
                        Named.of("Mission", mission),
                        Named.of("MissionOfTopicInterest", MissionOfTopicInterest.builder()
                                .id(missionOfTopicInterestId)
                                .topicOfInterest(topicOfInterest)
                                .mission(mission)
                                .build()
                        )
                )
        );
    }

    static Stream<Arguments> getterMissionProvider() {
        long missionId = 1L;
        Holiday holiday = new Holiday(true, true, true, true, true, true, true);
        String subject = "subject";
        MissionOfTopicInterest missionOfTopicInterest = MissionOfTopicInterest.builder()
                .id(missionId)
                .build();
        int numberOfParticipants = 3;
        String creator = "junwoo";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(7);
        List<MissionOfTopicInterest> missionOfTopicInterests = List.of(missionOfTopicInterest);
        return Stream.of(Arguments.arguments(
                        Named.of("MissionId", missionId),
                        Named.of("Subject", subject),
                        Named.of("Holiday", holiday),
                        Named.of("NumberOfParticipants", numberOfParticipants),
                        Named.of("Creator", creator),
                        Named.of("StartDate", startDate),
                        Named.of("EndDate", endDate),
                        Named.of("MissionOfTopicInterests", missionOfTopicInterests),
                        Named.of("Mission", Mission.builder()
                                .id(missionId)
                                .subject(subject)
                                .holiday(holiday)
                                .numberOfParticipants(numberOfParticipants)
                                .creator(creator)
                                .startDate(startDate)
                                .endDate(endDate)
                                .missionOfTopicInterests(missionOfTopicInterests)
                                .build()
                        )
                )
        );
    }

    static Stream<Arguments> getterTopicOfInterestProvider() {
        Long topicOfInterestId = 1L;
        String name = "Spring";
        MemberOfTopicInterest memberOfTopicInterest = MemberOfTopicInterest
                .builder()
                .build();
        List<MemberOfTopicInterest> memberOfTopicInterests = List.of(memberOfTopicInterest);
        return Stream.of(Arguments.arguments(
                        Named.of("topicOfInterestId", topicOfInterestId),
                        Named.of("Name", name),
                        Named.of("MemberOfTopicInterests", memberOfTopicInterests),
                        Named.of("TopicOfInterest", TopicOfInterest.builder()
                                .topicOfInterestId(topicOfInterestId)
                                .name(name)
                                .memberOfTopicInterests(memberOfTopicInterests)
                                .build()
                        )
                )
        );
    }

}
