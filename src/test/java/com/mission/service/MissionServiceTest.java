package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.RequestCreateMission;
import com.mission.dto.mission.RequestUpdateMission;
import com.mission.repository.MissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MissionServiceTest {

    @Autowired private MissionService missionService;
    @Autowired private MissionRepository missionRepository;
    private RequestCreateMission requestCreateMission;

    @BeforeEach
    void initRequestData() {
        requestCreateMission = MissionTestData.createMissionData();
    }

    @DisplayName("미션생성 테스트")
    @Transactional
    @Test
    public void create_mission_test() throws Exception {
        // when
        Long missionId = saveMission();
        Mission mission = missionRepository.findById(missionId)
                                           .orElseThrow(EntityNotFoundException::new);
        // then
        assertThat(missionId).isNotNull();
        assertThat(mission.getSubject()).isEqualTo(requestCreateMission.getSubject());
        assertThat(mission.getHoliday()).isEqualTo(requestCreateMission.getHoliday());
        assertThat(mission.getNumberOfParticipants()).isEqualTo(requestCreateMission.getNumberOfParticipants());
        assertThat(mission.getCreator()).isEqualTo(requestCreateMission.getCreator());
        assertThat(mission.getStartDate()).isEqualTo(requestCreateMission.getStartDate());
        assertThat(mission.getEndDate()).isEqualTo(requestCreateMission.getEndDate());
        IntStream.range(0, mission.getMissionOfTopicInterests().size())
                 .forEach(i -> assertThat(mission.getMissionOfTopicInterests().get(i).getTopicOfInterest().getName()).isEqualTo(requestCreateMission.getMissionOfTopicInterests().get(i)));
    }

    @DisplayName("미션 수정 테스트 - subject")
    @Transactional
    @Test
    public void update_mission_subject() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateSubjectData = MissionTestData.updateSubjectData(missionId);
        // when
        missionService.updateMissionInformation(updateSubjectData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getSubject()).isEqualTo(updateSubjectData.getSubject());
    }

    @DisplayName("미션 수정 테스트 - holiday")
    @Transactional
    @Test
    public void update_mission_holiday() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateHolidayData = MissionTestData.updateHolidayData(missionId);
        // when
        missionService.updateMissionInformation(updateHolidayData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getHoliday().isMonday()).isFalse();
    }

    @DisplayName("미션 수정 테스트 - numberOfParticipants")
    @Transactional
    @Test
    public void update_mission_numberOfParticipants() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateNumberOfParticipantsData = MissionTestData.updateNumberOfParticipantsData(missionId);
        // when
        missionService.updateMissionInformation(updateNumberOfParticipantsData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getNumberOfParticipants()).isEqualTo(updateNumberOfParticipantsData.getNumberOfParticipants());
    }

    @DisplayName("미션 수정 테스트 - creator")
    @Transactional
    @Test
    public void update_mission_creator() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateCreatorData = MissionTestData.updateCreatorData(missionId);
        // when
        missionService.updateMissionInformation(updateCreatorData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getCreator()).isEqualTo(updateCreatorData.getCreator());
    }

    @DisplayName("미션 수정 테스트 - startDate")
    @Transactional
    @Test
    public void update_mission_startDate() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateStartDateData = MissionTestData.updateStartDateData(missionId);
        // when
        missionService.updateMissionInformation(updateStartDateData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getStartDate()).isEqualTo(updateStartDateData.getStartDate());
    }

    @DisplayName("미션 수정 테스트 - endDate")
    @Transactional
    @Test
    public void update_mission_endDate() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateEndDateData = MissionTestData.updateEndDateData(missionId);
        // when
        missionService.updateMissionInformation(updateEndDateData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        assertThat(mission.getCreator()).isEqualTo(updateEndDateData.getCreator());
    }

    @DisplayName("미션 수정 테스트 - missionOfTopicInterests")
    @Transactional
    @Test
    public void update_mission_missionOfTopicInterests() throws Exception {
        // given
        Long missionId = saveMission();
        RequestUpdateMission updateMissionOfTopicInterestsData = MissionTestData.updateMissionOfTopicInterestsData(missionId);
        // when
        missionService.updateMissionInformation(updateMissionOfTopicInterestsData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        List<MissionOfTopicInterest> missionOfTopicInterests = mission.getMissionOfTopicInterests();
        assertThat(missionOfTopicInterests.size()).isEqualTo(3);
    }

    private Long saveMission() {
        return missionService.saveMission(requestCreateMission);
    }

}
