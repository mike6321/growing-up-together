package com.provider.mission;

import com.mission.domain.Holiday;
import com.mission.dto.mission.ResFindMission;
import com.mission.service.Utils;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ResFindMissionProvider {

    public static final String PROVIDER_CLASSPATH = "com.provider.mission.ResFindMissionProvider#";

    static Stream<Arguments> resFindMissionProvider() {
        return Stream.of(Arguments.of(
                Named.of("ResFindMission", ResFindMission
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
                )
        ));
    }

}
