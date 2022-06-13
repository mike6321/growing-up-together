package com.mission.repository;

import com.annotation.MockMvcTest;
import com.mission.domain.Holiday;
import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.service.MissionService;
import com.mission.service.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@MockMvcTest
class MissionRepositoryTest {

    @Autowired MissionRepository missionRepository;
    @Autowired MissionService missionService;
    @Autowired MissionFactory missionFactory;

    @DisplayName("미션 업데이트 테스트")
    @Test
    void update_mission_test() {
        // given
        Long missionId = missionFactory.createMission();
        ReqUpdateMission reqUpdateMission = initReqUpdateMission(missionId);
        // when
        missionService.updateMissionInformation(reqUpdateMission);
        Mission savedMission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        // then
        assertThat(savedMission.getSubject()).isEqualTo(reqUpdateMission.getSubject());
        assertThat(savedMission.getHoliday()).isEqualTo(reqUpdateMission.getHoliday());
        assertThat(savedMission.getNumberOfParticipants()).isEqualTo(reqUpdateMission.getNumberOfParticipants());
        assertThat(savedMission.getCreator()).isEqualTo(reqUpdateMission.getCreator());
        assertThat(savedMission.getStartDate()).isEqualTo(reqUpdateMission.getStartDate());
        assertThat(savedMission.getEndDate()).isEqualTo(reqUpdateMission.getEndDate());
        List<MissionOfTopicInterest> savedMissionMissionOfTopicInterests = savedMission.getMissionOfTopicInterests();
        savedMissionMissionOfTopicInterests.containsAll(reqUpdateMission.getMissionOfTopicInterests());
    }

    @DisplayName("미션 생성 테스트")
    @ParameterizedTest
    @MethodSource("reqCreateMissionProvider")
    void save_mission(ReqCreateMission reqCreateMission, List<MissionOfTopicInterest> missionOfTopicInterests) {
        // given
        Mission mission = new Mission();
        mission.createMission(reqCreateMission, missionOfTopicInterests);
        // when
        Mission savedMission = missionRepository.save(mission);
        // then
        assertThat(savedMission.getSubject()).isEqualTo(reqCreateMission.getSubject());
        assertThat(savedMission.getHoliday()).isEqualTo(reqCreateMission.getHoliday());
        assertThat(savedMission.getNumberOfParticipants()).isEqualTo(reqCreateMission.getNumberOfParticipants());
        assertThat(savedMission.getCreator()).isEqualTo(reqCreateMission.getCreator());
        assertThat(savedMission.getStartDate()).isEqualTo(reqCreateMission.getStartDate());
        assertThat(savedMission.getEndDate()).isEqualTo(reqCreateMission.getEndDate());
        assertThat(savedMission.getMissionOfTopicInterests()).containsAll(missionOfTopicInterests);
    }


    static Stream<Arguments> reqCreateMissionProvider() {
        Holiday holiday = new Holiday(true, true, true, true, true, true, true);
        String subject = "subject";
        int numberOfParticipants = 3;
        String creator = "junwoo";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(7);
        List<String> topicOfInterestNames = List.of("spring", "kafka");
        List<MissionOfTopicInterest> missionOfTopicInterests = MissionFactory.initMissionOfTopicInterests(topicOfInterestNames);
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

    private ReqUpdateMission initReqUpdateMission(Long missionId) {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return ReqUpdateMission
                .builder()
                .missionId(missionId)
                .subject("subject1")
                .holiday(new Holiday(false, true, true, true, true, true, true))
                .numberOfParticipants(1)
                .creator("sanghoon")
                .startDate(Utils.parseDate("2022/05/14"))
                .endDate(Utils.parseDate("2022/05/21"))
                .missionOfTopicInterests(missionOfTopicInterests)
                .build();
    }

}
