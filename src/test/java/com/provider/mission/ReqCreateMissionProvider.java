package com.provider.mission;

import com.mission.domain.Holiday;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.service.Utils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ReqCreateMissionProvider {

    public static final String PROVIDER_CLASSPATH = "com.provider.mission.ReqCreateMissionProvider#";

    static Stream<Arguments> reqCreateMissionProvider() {
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

}
