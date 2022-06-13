package com.mission.controller;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.service.MissionService;
import com.validation.CreateMissionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private final CreateMissionValidator createMissionValidator;

    @InitBinder("reqCreateMission")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createMissionValidator);
    }

    @GetMapping("/mission")
    public ResponseEntity<List<ResFindMission>> findMissions() {
        List<ResFindMission> missions = missionService.getMissions();
        return ResponseEntity.ok(missions);
    }

    @GetMapping("/mission/{id}")
    public ResponseEntity<ResFindMission> findMission(@PathVariable(name = "id") Long missionId) {
        ResFindMission mission = missionService.getMission(missionId);
        return ResponseEntity.ok(mission);
    }

    @PostMapping("/mission")
    public ResponseEntity<Long> createMission(@Valid @RequestBody ReqCreateMission reqCreateMission) {
        return ResponseEntity.ok(missionService.saveMission(reqCreateMission));
    }

    @PutMapping("/mission")
    public ResponseEntity<Long> updateMission(@Valid @RequestBody ReqUpdateMission requestUpdateMission) {
        return ResponseEntity.ok(missionService.updateMissionInformation(requestUpdateMission));
    }

}
