package com.mission.dto.mission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@SuperBuilder
public class RequestUpdateMission extends RequestCreateMission {

    @NotNull
    private Long missionId;

}
