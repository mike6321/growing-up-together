package com.mission.controller;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.service.MissionService;
import com.validation.CreateMissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private final CreateMissionValidator createMissionValidator;

    @InitBinder("requestCreateMission")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMissionValidator);
    }

    @PostMapping("/mission")
    public ResponseEntity<?> createMission(@Valid @RequestBody ReqCreateMission reqCreateMission) {
        return ResponseEntity.ok(missionService.saveMission(reqCreateMission));
    }

    @PutMapping("/mission")
    public ResponseEntity<?> updateMission(@Valid @RequestBody ReqUpdateMission requestUpdateMission) {
        return ResponseEntity.ok(missionService.updateMissionInformation(requestUpdateMission));
    }

}
