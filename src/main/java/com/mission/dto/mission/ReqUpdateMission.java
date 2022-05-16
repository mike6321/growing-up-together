package com.mission.dto.mission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReqUpdateMission extends ReqCreateMission {

    @NotNull
    private Long missionId;

}
