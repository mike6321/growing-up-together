package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
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
    private ReqCreateMission reqCreateMission;

    @BeforeEach
    void initRequestData() {
        reqCreateMission = MissionTestData.createMissionData();
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
        assertThat(mission.getSubject()).isEqualTo(reqCreateMission.getSubject());
        assertThat(mission.getHoliday()).isEqualTo(reqCreateMission.getHoliday());
        assertThat(mission.getNumberOfParticipants()).isEqualTo(reqCreateMission.getNumberOfParticipants());
        assertThat(mission.getCreator()).isEqualTo(reqCreateMission.getCreator());
        assertThat(mission.getStartDate()).isEqualTo(reqCreateMission.getStartDate());
        assertThat(mission.getEndDate()).isEqualTo(reqCreateMission.getEndDate());
        IntStream.range(0, mission.getMissionOfTopicInterests().size())
                 .forEach(i -> assertThat(mission.getMissionOfTopicInterests().get(i).getTopicOfInterest().getName()).isEqualTo(reqCreateMission.getMissionOfTopicInterests().get(i)));
    }

    @DisplayName("미션 수정 테스트 - subject")
    @Transactional
    @Test
    public void update_mission_subject() throws Exception {
        // given
        Long missionId = saveMission();
        ReqUpdateMission updateSubjectData = MissionTestData.updateSubjectData(missionId);
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
        ReqUpdateMission updateHolidayData = MissionTestData.updateHolidayData(missionId);
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
        ReqUpdateMission updateNumberOfParticipantsData = MissionTestData.updateNumberOfParticipantsData(missionId);
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
        ReqUpdateMission updateCreatorData = MissionTestData.updateCreatorData(missionId);
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
        ReqUpdateMission updateStartDateData = MissionTestData.updateStartDateData(missionId);
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
        ReqUpdateMission updateEndDateData = MissionTestData.updateEndDateData(missionId);
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
        ReqUpdateMission updateMissionOfTopicInterestsData = MissionTestData.updateMissionOfTopicInterestsData(missionId);
        // when
        missionService.updateMissionInformation(updateMissionOfTopicInterestsData);
        // then
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        List<MissionOfTopicInterest> missionOfTopicInterests = mission.getMissionOfTopicInterests();
        assertThat(missionOfTopicInterests.size()).isEqualTo(3);
    }

    private Long saveMission() {
        return missionService.saveMission(reqCreateMission);
    }

}
