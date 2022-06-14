package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.repository.MissionRepository;
import com.provider.mission.MissionProvider;
import com.provider.mission.ReqUpdateMissionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MissionServiceTest {

    @InjectMocks MissionService missionService;
    @Mock MissionRepository missionRepository;
    @Mock TopicOfInterestService topicOfInterestService;

    @DisplayName("미션 전체 조회 테스트")
    @ParameterizedTest
    @MethodSource(MissionProvider.PROVIDER_CLASSPATH + "missionProvider")
    void get_missions_test(Mission reqMission) {
        // given
        List<Mission> reqMissions = List.of(reqMission);
        given(missionRepository.findAll()).willReturn(reqMissions);
        // when
        List<ResFindMission> missions = missionService.getMissions();
        ResFindMission resFindMission = missions.get(0);
        // then
        assertThat(reqMissions.size()).isEqualTo(missions.size());
        assertThat(resFindMission.getMissionId()).isEqualTo(reqMission.getId());
        assertThat(resFindMission.getSubject()).isEqualTo(reqMission.getSubject());
        assertThat(resFindMission.getHoliday()).isEqualTo(reqMission.getHoliday());
        assertThat(resFindMission.getNumberOfParticipants()).isEqualTo(reqMission.getNumberOfParticipants());
        assertThat(resFindMission.getCreator()).isEqualTo(reqMission.getCreator());
        assertThat(resFindMission.getStartDate()).isEqualTo(reqMission.getStartDate());
        assertThat(resFindMission.getEndDate()).isEqualTo(reqMission.getEndDate());
        List<String> reqTopicOfInterestNames = reqMission.getMissionOfTopicInterests()
                .stream()
                .map(MissionOfTopicInterest::getTopicOfInterest)
                .map(TopicOfInterest::getName)
                .collect(Collectors.toList());
        assertThat(resFindMission.getTopicOfInterests()).isEqualTo(reqTopicOfInterestNames);
    }

    @DisplayName("미션 조회 테스트")
    @ParameterizedTest
    @MethodSource(MissionProvider.PROVIDER_CLASSPATH + "missionProvider")
    void get_mission_test(Mission mission) {
        // given
        given(missionRepository.findById(anyLong())).willReturn(Optional.of(mission));
        // when
        ResFindMission resFindMission = missionService.getMission(1L);
        // then
        assertThat(resFindMission.getMissionId()).isEqualTo(mission.getId());
        assertThat(resFindMission.getSubject()).isEqualTo(mission.getSubject());
        assertThat(resFindMission.getHoliday()).isEqualTo(mission.getHoliday());
        assertThat(resFindMission.getNumberOfParticipants()).isEqualTo(mission.getNumberOfParticipants());
        assertThat(resFindMission.getCreator()).isEqualTo(mission.getCreator());
        assertThat(resFindMission.getStartDate()).isEqualTo(mission.getStartDate());
        assertThat(resFindMission.getEndDate()).isEqualTo(mission.getEndDate());
        List<String> reqTopicOfInterestNames = mission.getMissionOfTopicInterests()
                .stream()
                .map(MissionOfTopicInterest::getTopicOfInterest)
                .map(TopicOfInterest::getName)
                .collect(Collectors.toList());
        assertThat(resFindMission.getTopicOfInterests()).isEqualTo(reqTopicOfInterestNames);
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
    @MethodSource(ReqUpdateMissionProvider.PROVIDER_CLASSPATH + "reqUpdateMissionProvider")
    void update_mission_information_test(ReqUpdateMission reqUpdateMission) {
        // given
        given(missionRepository.findById(reqUpdateMission.getMissionId())).willReturn(Optional.of(Mission.of(reqUpdateMission)));
        given(missionRepository.save(any(Mission.class))).willReturn(Mission.of(reqUpdateMission));
        // when
        Long missionId = missionService.updateMissionInformation(reqUpdateMission);
        // then
        then(missionId).equals(reqUpdateMission.getMissionId());
    }

}
