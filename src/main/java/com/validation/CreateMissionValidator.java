package com.validation;

import com.mission.dto.mission.RequestCreateMission;
import com.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class CreateMissionValidator implements Validator {

    private final MissionRepository missionRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestCreateMission.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestCreateMission requestCreateMission = (RequestCreateMission) target;
        if (missionRepository.existsBySubject(requestCreateMission.getSubject())) {
            errors.rejectValue("subject", "invalid.subject", new Object[]{requestCreateMission.getSubject()}, "이미 사용중인 주제입니다.");
        }
    }

}
