package com.mission.repository;

import com.mission.domain.Holiday;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.service.MissionService;
import com.mission.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MissionFactory {

    @Autowired
    private MissionService missionService;

    public Long createMission() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka");
        ReqCreateMission reqCreateMission = ReqCreateMission.builder()
                .subject("subject")
                .holiday(new Holiday(true, true, true, true, true, true, true))
                .numberOfParticipants(3)
                .startDate(Utils.parseDate("2022/05/13"))
                .endDate(Utils.parseDate("2022/05/20"))
                .missionOfTopicInterests(missionOfTopicInterests)
                .build();
        return missionService.saveMission(reqCreateMission);
    }

    static List<MissionOfTopicInterest> initMissionOfTopicInterests(List<String> topicOfInterestNames) {
        List<TopicOfInterest> topicOfInterests = TopicOfInterest.nonExistsTopic(topicOfInterestNames, List.of("vue.js"));
        return MissionOfTopicInterest.of(topicOfInterests);
    }

}
