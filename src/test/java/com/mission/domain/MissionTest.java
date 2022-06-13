package com.mission.domain;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.provider.mission.MixProvider;
import com.provider.mission.ReqUpdateMissionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionTest {

    @DisplayName("Mission - getter test")
    @ParameterizedTest
    @MethodSource(MixProvider.PROVIDER_CLASSPATH + "getterMissionProvider")
    void getter_test(Long inputId,
                    String inputSubject,
                    Holiday inputHoliday,
                    int inputNumberOfParticipants,
                    String inputCreator,
                    LocalDateTime inputStartDate,
                    LocalDateTime inputEndDate,
                    List<MissionOfTopicInterest> inputMissionOfTopicInterests,
                    Mission mission) {
        assertThat(mission.getId()).isEqualTo(inputId);
        assertThat(mission.getSubject()).isEqualTo(inputSubject);
        assertThat(mission.getHoliday()).isEqualTo(inputHoliday);
        assertThat(mission.getNumberOfParticipants()).isEqualTo(inputNumberOfParticipants);
        assertThat(mission.getCreator()).isEqualTo(inputCreator);
        assertThat(mission.getStartDate()).isEqualTo(inputStartDate);
        assertThat(mission.getEndDate()).isEqualTo(inputEndDate);
        assertThat(mission.getMissionOfTopicInterests()).isEqualTo(inputMissionOfTopicInterests);
    }

    @DisplayName("미션 생성 테스트")
    @ParameterizedTest
    @MethodSource(MixProvider.PROVIDER_CLASSPATH + "reqCreateMissionAndMissionOfTopicInterestsProvider")
    void create_mission_test(ReqCreateMission reqCreateMission, List<MissionOfTopicInterest> missionOfTopicInterests) {
        Mission mission = new Mission();
        mission.createMission(reqCreateMission, missionOfTopicInterests);
        assertThat(mission.getMissionOfTopicInterests().size()).isEqualTo(2);
        assertThat(mission.getMissionOfTopicInterests().get(0).getMission()).isNotNull();
    }

    @DisplayName("타입 변환 테스트 (ReqUpdateMission -> Mission)")
    @ParameterizedTest
    @MethodSource(ReqUpdateMissionProvider.PROVIDER_CLASSPATH + "reqUpdateMissionProvider")
    void mission_of_test(ReqUpdateMission reqUpdateMission) {
        Mission mission = Mission.of(reqUpdateMission);
        assertThat(mission.getId()).isEqualTo(reqUpdateMission.getMissionId());
        assertThat(mission.getSubject()).isEqualTo(reqUpdateMission.getSubject());
        assertThat(mission.getHoliday()).isEqualTo(reqUpdateMission.getHoliday());
        assertThat(mission.getNumberOfParticipants()).isEqualTo(reqUpdateMission.getNumberOfParticipants());
        assertThat(mission.getCreator()).isEqualTo(reqUpdateMission.getCreator());
        assertThat(mission.getStartDate()).isEqualTo(reqUpdateMission.getStartDate());
        assertThat(mission.getEndDate()).isEqualTo(reqUpdateMission.getEndDate());
    }

}
