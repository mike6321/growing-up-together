package com.provider;

import com.mission.domain.Holiday;
import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.service.Utils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class MissionProvider {

    static Stream<Arguments> requestMissionCreateProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return Stream.of(Arguments.of(
                ReqCreateMission
                        .builder()
                        .subject("subject")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(1)
                        .creator("junwoo.choi@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

    static Stream<Arguments> requestMissionUpdateProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return Stream.of(Arguments.of(
                ReqUpdateMission
                        .builder()
                        .missionId(1L)
                        .subject("subject")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(1)
                        .creator("junwoo.choi@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

    static Stream<Arguments> resFindMissionProvider() {
        return Stream.of(Arguments.of(
                ResFindMission
                        .builder()
                        .missionId(1L)
                        .subject("주제1")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(3)
                        .creator("test_account1@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .topicOfInterests(List.of("spring", "kafka", "vue.js"))
                        .build()
        ));
    }

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
