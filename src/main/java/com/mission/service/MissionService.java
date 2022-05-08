package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.dto.mission.RequestCreateMission;
import com.mission.dto.mission.RequestUpdateMission;
import com.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final TopicOfInterestService topicOfInterestService;
    private final MissionRepository missionRepository;

    @Transactional
    public Long saveMission(RequestCreateMission requestCreateMission) {
        List<MissionOfTopicInterest> missionOfTopicInterests = getMissionOfTopicInterests(requestCreateMission);
        Mission mission = new Mission();
        mission.createMission(requestCreateMission, missionOfTopicInterests);
        return missionRepository.save(mission)
                                .getId();
    }

    @Transactional
    public Long updateMissionInformation(RequestUpdateMission requestUpdateMission) {
        Long missionId = requestUpdateMission.getMissionId();
        Mission mission = missionRepository.findById(missionId)
                                           .orElseThrow(EntityNotFoundException::new);

        List<MissionOfTopicInterest> missionOfTopicInterests = getMissionOfTopicInterests(requestUpdateMission);

        mission.createMission(requestUpdateMission, missionOfTopicInterests);

        return missionRepository.save(mission)
                                .getId();
    }

    private List<MissionOfTopicInterest> getMissionOfTopicInterests(RequestCreateMission requestCreateMission) {
        List<MissionOfTopicInterest> missionOfTopicInterests = topicOfInterestService.getMissionOfTopicInterests(requestCreateMission);
        return missionOfTopicInterests;
    }

}
