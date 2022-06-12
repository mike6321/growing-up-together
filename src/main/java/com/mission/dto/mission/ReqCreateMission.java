package com.mission.dto.mission;

import com.mission.domain.Holiday;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReqCreateMission {

    @NotEmpty
    private String subject;
    @NotNull
    private Holiday holiday;
    @Min(value = 1)
    private int numberOfParticipants;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotEmpty
    private List<String> missionOfTopicInterests;

}
