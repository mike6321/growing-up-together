package com.mission.service;

import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.repository.MissionOfTopicInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicOfInterestService {

    private final MissionOfTopicInterestRepository missionOfTopicInterestRepository;

    public List<MissionOfTopicInterest> getMissionOfTopicInterests(ReqCreateMission reqCreateMission) {
        List<TopicOfInterest> topicOfInterests = createMissionOfTopicInterests(reqCreateMission.getMissionOfTopicInterests());
        List<MissionOfTopicInterest> missionOfTopicInterests = MissionOfTopicInterest.of(topicOfInterests);
        missionOfTopicInterestRepository.saveAll(missionOfTopicInterests);
        return missionOfTopicInterests;
    }

    protected List<TopicOfInterest> createMissionOfTopicInterests(List<String> missionOfTopicInterestsNames) {
        return TopicOfInterest.createTopicOfInterestName(missionOfTopicInterestsNames);
    }

}
