package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final TopicOfInterestService topicOfInterestService;
    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public List<ResFindMission> getMissions() {
        return missionRepository.findAll()
                .stream()
                .map(ResFindMission::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResFindMission getMission(Long missionId) {
        return missionRepository.findById(missionId)
                .stream()
                .map(ResFindMission::of)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    public Long saveMission(ReqCreateMission reqCreateMission) {
        List<MissionOfTopicInterest> missionOfTopicInterests = getMissionOfTopicInterests(reqCreateMission);
        Mission mission = new Mission();
        mission.createMission(reqCreateMission, missionOfTopicInterests);
        return missionRepository.save(mission)
                .getId();
    }

    public Long updateMissionInformation(ReqUpdateMission requestUpdateMission) {
        Long missionId = requestUpdateMission.getMissionId();
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(EntityNotFoundException::new);
        List<MissionOfTopicInterest> missionOfTopicInterests = getMissionOfTopicInterests(requestUpdateMission);
        mission.createMission(requestUpdateMission, missionOfTopicInterests);
        return missionRepository.save(mission)
                .getId();
    }

    private List<MissionOfTopicInterest> getMissionOfTopicInterests(ReqCreateMission reqCreateMission) {
        return topicOfInterestService.getMissionOfTopicInterests(reqCreateMission);
    }

}
