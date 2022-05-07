package com.mission.controller;

import com.mission.dto.mission.RequestCreateMission;
import com.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/mission")
    public void createMission(@RequestBody RequestCreateMission requestCreateMission) {
        missionService.saveMission(requestCreateMission);
    }

}
