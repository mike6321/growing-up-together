package com.mission.service;

import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.RequestCreateMission;
import com.mission.repository.TopicOfInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final TopicOfInterestRepository topicOfInterestRepository;

//    @Transactional
    public void saveMission(final RequestCreateMission requestCreateMission) {
        List<String> missionOfTopicInterests = requestCreateMission.getMissionOfTopicInterests();
    }

    @Transactional
    public void createMissionOfTopicInterests(List<String> missionOfTopicInterestsNames) {
        List<TopicOfInterest> topicOfInterests = TopicOfInterest.createTopicOfInterestName(missionOfTopicInterestsNames);
        topicOfInterestRepository.saveAll(topicOfInterests);
    }

}
