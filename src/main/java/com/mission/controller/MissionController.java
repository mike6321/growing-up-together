package com.mission.controller;

import com.mission.dto.mission.RequestCreateMission;
import com.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/mission")
    public ResponseEntity<?> createMission(@RequestBody RequestCreateMission requestCreateMission) {
        return ResponseEntity.ok(missionService.saveMission(requestCreateMission));
    }

    @PutMapping("/mission")
    public ResponseEntity<?> updateMission(@RequestBody RequestCreateMission requestCreateMission) {
        return ResponseEntity.ok(missionService.updateMissionInformation(requestCreateMission));
    }

}
