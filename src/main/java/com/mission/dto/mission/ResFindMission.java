package com.mission.dto.mission;

import com.mission.domain.Holiday;
import com.mission.domain.Mission;
import com.mission.domain.MissionOfTopicInterest;
import com.mission.domain.TopicOfInterest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ResFindMission {

    private Long missionId;
    private String subject;
    private Holiday holiday;
    private int numberOfParticipants;
    private String creator;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> topicOfInterests;

    public static ResFindMission of(Mission mission) {
        return ResFindMission
                .builder()
                .missionId(mission.getId())
                .subject(mission.getSubject())
                .holiday(mission.getHoliday())
                .numberOfParticipants(mission.getNumberOfParticipants())
                .creator(mission.getCreator())
                .startDate(mission.getStartDate())
                .endDate(mission.getEndDate())
                .topicOfInterests(
                        mission.getMissionOfTopicInterests()
                                .stream()
                                .map(MissionOfTopicInterest::getTopicOfInterest)
                                .map(TopicOfInterest::getName)
                                .collect(Collectors.toList())
                )
                .build();
    }

}
