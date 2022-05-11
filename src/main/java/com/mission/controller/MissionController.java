package com.mission.controller;

import com.mission.dto.mission.RequestCreateMission;
import com.mission.dto.mission.RequestUpdateMission;
import com.mission.service.MissionService;
import com.validation.CreateMissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private final CreateMissionValidator createMissionValidator;

    @InitBinder("requestCreateMission")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMissionValidator);
    }

    @PostMapping("/mission")
    public ResponseEntity<?> createMission(@Valid @RequestBody RequestCreateMission requestCreateMission) {
        return ResponseEntity.ok(missionService.saveMission(requestCreateMission));
    }

    @PutMapping("/mission")
    public ResponseEntity<?> updateMission(@Valid @RequestBody RequestUpdateMission requestUpdateMission) {
        return ResponseEntity.ok(missionService.updateMissionInformation(requestUpdateMission));
    }

}
