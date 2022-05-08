package com.mission.dto.mission;

import com.mission.domain.Holiday;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RequestCreateMission {

    private String subject;
    private Holiday holiday;
    private int numberOfParticipants;
    private String creator;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> missionOfTopicInterests;

}
