package com.mission.dto.mission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mission.domain.Holiday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateMission {

    private Long missionId;
    private String subject;
    private Holiday holiday;
    private int numberOfParticipants;
    private String creator;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> missionOfTopicInterests;

}
