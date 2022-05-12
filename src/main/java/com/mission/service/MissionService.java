package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {

    private final TopicOfInterestService topicOfInterestService;
    private final MissionRepository missionRepository;

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
