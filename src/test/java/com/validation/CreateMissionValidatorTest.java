package com.validation;

import com.mission.GrowingUpTogetherApplication;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.repository.MissionRepository;
import com.provider.mission.ReqCreateMissionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GrowingUpTogetherApplication.class)
class CreateMissionValidatorTest {

    @InjectMocks
    private CreateMissionValidator createMissionValidator;
    @Mock
    private MissionRepository missionRepository;

    @DisplayName("주제가 이미 사용 O")
    @ParameterizedTest
    @MethodSource(ReqCreateMissionProvider.PROVIDER_CLASSPATH + "reqCreateMissionProvider")
    void already_use_subject_validation_test(ReqCreateMission reqCreateMission) {
        // given
        Errors errors = new BeanPropertyBindingResult(reqCreateMission, "reqCreateMission");
        assertThat(reqCreateMission).isNotNull();
        given(missionRepository.existsBySubject(anyString())).willReturn(true);
        // when
        createMissionValidator.validate(reqCreateMission, errors);
        // then
        ObjectError allError = errors.getAllErrors().get(0);
        assertThat(allError.getDefaultMessage()).isEqualTo("이미 사용중인 주제입니다.");
    }

    @DisplayName("주제가 이미 사용 X")
    @ParameterizedTest
    @MethodSource(ReqCreateMissionProvider.PROVIDER_CLASSPATH + "reqCreateMissionProvider")
    void non_use_subject_validation_test(ReqCreateMission reqCreateMission) {
        // given
        Errors errors = new BeanPropertyBindingResult(reqCreateMission, "reqCreateMission");
        assertThat(reqCreateMission).isNotNull();
        given(missionRepository.existsBySubject(anyString())).willReturn(false);
        // when
        createMissionValidator.validate(reqCreateMission, errors);
        // then
        assertThat(errors.getAllErrors()).isEmpty();
    }

}
