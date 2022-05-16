package com.mission.domain;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.service.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionTest {

    @DisplayName("Mission - getter test")
    @ParameterizedTest
    @MethodSource("missionProvider")
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
    @MethodSource("reqCreateMissionProvider")
    void create_mission_test(ReqCreateMission reqCreateMission, List<MissionOfTopicInterest> missionOfTopicInterests) {
        Mission mission = new Mission();
        mission.createMission(reqCreateMission, missionOfTopicInterests);
        assertThat(mission.getMissionOfTopicInterests().size()).isEqualTo(2);
        assertThat(mission.getMissionOfTopicInterests().get(0).getMission()).isNotNull();
    }

    @DisplayName("타입 변환 테스트 (ReqUpdateMission -> Mission)")
    @ParameterizedTest
    @MethodSource("requestMissionProvider")
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

    static Stream<Arguments> requestMissionProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka");
        return Stream.of(Arguments.of(
                ReqUpdateMission
                        .builder()
                        .missionId(1L)
                        .subject("subject1")
                        .holiday(new Holiday(true, true, true, true, true, true, true))
                        .numberOfParticipants(3)
                        .creator("junwoo")
                        .startDate(Utils.parseDate("2022/05/13"))
                        .endDate(Utils.parseDate("2022/05/20"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

    static Stream<Arguments> missionProvider() {
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
                missionId, subject, holiday, numberOfParticipants, creator, startDate, endDate, missionOfTopicInterests,
                Mission.builder()
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
        );
    }

    static Stream<Arguments> reqCreateMissionProvider() {
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
                ReqCreateMission.builder()
                        .subject(subject)
                        .holiday(holiday)
                        .numberOfParticipants(numberOfParticipants)
                        .creator(creator)
                        .startDate(startDate)
                        .endDate(endDate)
                        .missionOfTopicInterests(topicOfInterestNames)
                        .build(),
                missionOfTopicInterests));
    }

}
