package com.validation;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class CreateMissionValidator implements Validator {

    private final MissionRepository missionRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ReqCreateMission.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReqCreateMission reqCreateMission = (ReqCreateMission) target;
        if (missionRepository.existsBySubject(reqCreateMission.getSubject())) {
            errors.rejectValue("subject", "invalid.subject", new Object[]{reqCreateMission.getSubject()}, "이미 사용중인 주제입니다.");
        }
    }

}
