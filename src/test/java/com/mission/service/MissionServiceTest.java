package com.mission.service;

import com.mission.domain.Holiday;
import com.mission.domain.Mission;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.repository.MissionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionServiceTest {

    @InjectMocks MissionService missionService;
    @Mock MissionRepository missionRepository;
    @Mock TopicOfInterestService topicOfInterestService;
    private static final String PROVIDER_CLASSPATH = "com.provider.MissionProvider#";

    @DisplayName("미션 전체 조회 테스트")
    @ParameterizedTest
    @MethodSource(PROVIDER_CLASSPATH + "missionProvider")
    void get_mission_test(Mission reqMission) {
        // given
        List<Mission> reqMissions = List.of(reqMission);
        given(missionRepository.findAll()).willReturn(reqMissions);
        // when
        List<ResFindMission> missions = missionService.getMissions();
        Mission resultMission = reqMissions.get(0);
        // then
        assertThat(reqMissions.size()).isEqualTo(missions.size());
        assertThat(resultMission.getId()).isEqualTo(reqMission.getId());
        assertThat(resultMission.getSubject()).isEqualTo(reqMission.getSubject());
        assertThat(resultMission.getHoliday()).isEqualTo(reqMission.getHoliday());
        assertThat(resultMission.getNumberOfParticipants()).isEqualTo(reqMission.getNumberOfParticipants());
        assertThat(resultMission.getCreator()).isEqualTo(reqMission.getCreator());
        assertThat(resultMission.getStartDate()).isEqualTo(reqMission.getStartDate());
        assertThat(resultMission.getEndDate()).isEqualTo(reqMission.getEndDate());
        assertThat(resultMission.getMissionOfTopicInterests()).isEqualTo(reqMission.getMissionOfTopicInterests());
    }

    @DisplayName("미션 저장 테스트")
    @ParameterizedTest
    @CsvSource(textBlock = "subject, false true true true true true true, 3, junwoo, 2022/05/13, 2022/05/20, spring kafka")
    void save_mission_test(@AggregateWith(ReqCreateMissionAggregator.class)
                                   ReqCreateMission reqCreateMission) {
        // given
        given(missionRepository.save(any(Mission.class))).willReturn(
                Mission.builder()
                        .id(1L)
                        .build()
        );
        // when
        missionService.saveMission(reqCreateMission);
        // then
    }

    @DisplayName("미션 업데이트 테스트")
    @ParameterizedTest
    @MethodSource("requestMissionUpdateProvider")
    void update_mission_information_test(ReqUpdateMission reqUpdateMission) {
        // given
        given(missionRepository.findById(reqUpdateMission.getMissionId())).willReturn(Optional.of(Mission.of(reqUpdateMission)));
        given(missionRepository.save(any(Mission.class))).willReturn(Mission.of(reqUpdateMission));
        // when
        Long missionId = missionService.updateMissionInformation(reqUpdateMission);
        // then
        then(missionId).equals(reqUpdateMission.getMissionId());
    }

    static Stream<Arguments> requestMissionUpdateProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return Stream.of(Arguments.of(
                ReqUpdateMission
                        .builder()
                        .missionId(1L)
                        .subject("subject1")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(1)
                        .creator("sanghoon")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

}
