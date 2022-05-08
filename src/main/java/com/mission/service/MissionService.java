package com.mission.service;

import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.RequestCreateMission;
import com.mission.repository.MissionRepository;
import com.mission.repository.TopicOfInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final TopicOfInterestRepository topicOfInterestRepository;
    private final MissionRepository missionRepository;

    @Transactional
    public Long saveMission(final RequestCreateMission requestCreateMission) {
        List<TopicOfInterest> topicOfInterests = createMissionOfTopicInterests(requestCreateMission.getMissionOfTopicInterests());
        List<MissionOfTopicInterest> missionOfTopicInterests = topicOfInterests.stream()
                                                                               .map(MissionOfTopicInterest::new)
                                                                               .collect(Collectors.toList());

        Mission createMission = Mission.createMission(requestCreateMission, missionOfTopicInterests);
        return missionRepository.save(createMission)
                                .getId();
    }

    @Transactional
    public List<TopicOfInterest> createMissionOfTopicInterests(List<String> missionOfTopicInterestsNames) {
        List<String> existsTopicOfInterests = topicOfInterestRepository.findByNameIn(missionOfTopicInterestsNames)
                                                                       .stream()
                                                                       .map(TopicOfInterest::getName)
                                                                       .collect(Collectors.toList());
        List<TopicOfInterest> nonExistsTopicOfInterests = TopicOfInterest.nonExistsTopic(missionOfTopicInterestsNames, existsTopicOfInterests);
        topicOfInterestRepository.saveAll(nonExistsTopicOfInterests);
        return TopicOfInterest.createTopicOfInterestName(missionOfTopicInterestsNames);
    }

}
