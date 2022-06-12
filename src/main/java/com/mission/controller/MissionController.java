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

    // TODO: 2022/06/07 미션 조회 기능 개발 (1)
    @GetMapping("/mission")
    public ResponseEntity<?> findMissions() {
        return null;
    }

    // TODO: 2022/06/07 미션 조회 기능 개발 (2)
    @GetMapping("/mission/{id}")
    public ResponseEntity<?> findMission(@PathVariable(name = "id") Long missionId) {
        return null;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(null);
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
