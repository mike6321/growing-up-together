package com.mission.dto.mission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@SuperBuilder
public class ReqUpdateMission extends ReqCreateMission {

    @NotNull
    private Long missionId;

}
