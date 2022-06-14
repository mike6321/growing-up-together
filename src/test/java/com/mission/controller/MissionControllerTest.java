package com.mission.controller;

import com.annotation.MockMvcTest;
import com.annotation.WithAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.dto.mission.ResFindMission;
import com.mission.service.MissionService;
import com.provider.mission.ReqCreateMissionProvider;
import com.provider.mission.ReqUpdateMissionProvider;
import com.provider.mission.ResFindMissionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MissionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MissionService missionService;
    private static final String PATH = "/api/mission";

    @Order(1)
    @DisplayName("미션 전체 조회 컨트롤러 테스트")
    @WithAccount(email = "test_account1@test.com", nickname = "test_account1")
    @ParameterizedTest
    @MethodSource(ResFindMissionProvider.PROVIDER_CLASSPATH + "resFindMissionProvider")
    void find_missions_test(ResFindMission resFindMission) throws Exception {
        // given
        given(missionService.getMissions()).willReturn(List.of(resFindMission));
        // then
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].missionId").value(resFindMission.getMissionId()))
                .andExpect(jsonPath("$[0].subject", is(resFindMission.getSubject())))
                .andExpect(jsonPath("$[0].holiday.monday", is(resFindMission.getHoliday().isMonday())))
                .andExpect(jsonPath("$[0].holiday.tuesday", is(resFindMission.getHoliday().isTuesday())))
                .andExpect(jsonPath("$[0].holiday.wednesday", is(resFindMission.getHoliday().isWednesday())))
                .andExpect(jsonPath("$[0].holiday.thursday", is(resFindMission.getHoliday().isThursday())))
                .andExpect(jsonPath("$[0].holiday.friday", is(resFindMission.getHoliday().isFriday())))
                .andExpect(jsonPath("$[0].holiday.saturday", is(resFindMission.getHoliday().isSaturday())))
                .andExpect(jsonPath("$[0].holiday.sunday", is(resFindMission.getHoliday().isSunday())))
                .andExpect(jsonPath("$[0].numberOfParticipants", is(resFindMission.getNumberOfParticipants())))
                .andExpect(jsonPath("$[0].creator", is(resFindMission.getCreator())))
                .andExpect(jsonPath("$[0].startDate", is(resFindMission.getStartDate().toString())))
                .andExpect(jsonPath("$[0].endDate", is(resFindMission.getEndDate().toString())))
                .andExpect(jsonPath("$[0].topicOfInterests", is(resFindMission.getTopicOfInterests())))
                .andDo(print());
    }

    @Order(2)
    @DisplayName("미션 조회 컨트롤러 테스트")
    @WithAccount(email = "test_account2@test.com", nickname = "test_account1")
    @ParameterizedTest
    @MethodSource(ResFindMissionProvider.PROVIDER_CLASSPATH + "resFindMissionProvider")
    public void find_mission_test(ResFindMission resFindMission) throws Exception {
        // given
        given(missionService.getMission(anyLong())).willReturn(resFindMission);
        // then
        mockMvc.perform(get(PATH + "/{missionId}", resFindMission.getMissionId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.missionId").value(resFindMission.getMissionId()))
                .andExpect(jsonPath("$.subject", is(resFindMission.getSubject())))
                .andExpect(jsonPath("$.holiday.monday", is(resFindMission.getHoliday().isMonday())))
                .andExpect(jsonPath("$.holiday.tuesday", is(resFindMission.getHoliday().isTuesday())))
                .andExpect(jsonPath("$.holiday.wednesday", is(resFindMission.getHoliday().isWednesday())))
                .andExpect(jsonPath("$.holiday.thursday", is(resFindMission.getHoliday().isThursday())))
                .andExpect(jsonPath("$.holiday.friday", is(resFindMission.getHoliday().isFriday())))
                .andExpect(jsonPath("$.holiday.saturday", is(resFindMission.getHoliday().isSaturday())))
                .andExpect(jsonPath("$.holiday.sunday", is(resFindMission.getHoliday().isSunday())))
                .andExpect(jsonPath("$.numberOfParticipants", is(resFindMission.getNumberOfParticipants())))
                .andExpect(jsonPath("$.creator", is(resFindMission.getCreator())))
                .andExpect(jsonPath("$.startDate", is(resFindMission.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(resFindMission.getEndDate().toString())))
                .andExpect(jsonPath("$.topicOfInterests", is(resFindMission.getTopicOfInterests())))
                .andDo(print());
    }

    @Order(3)
    @DisplayName("미션 생성 컨트롤러 테스트")
    @ParameterizedTest
    @MethodSource(ReqCreateMissionProvider.PROVIDER_CLASSPATH + "reqCreateMissionProvider")
    @WithAccount(email = "test_account3@test.com", nickname = "test_account1")
    void create_mission_test(ReqCreateMission reqCreateMission) throws Exception {
        // given
        given(missionService.saveMission(any())).willReturn(1L);
        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(reqCreateMission);
        // then
        mockMvc.perform(post(PATH)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @Order(4)
    @DisplayName("미션 수정 컨트롤러 테스트")
    @ParameterizedTest
    @MethodSource(ReqUpdateMissionProvider.PROVIDER_CLASSPATH + "reqUpdateMissionProvider")
    @WithAccount(email = "test_account4@test.com", nickname = "test_account2")
    void update_mission_test(ReqUpdateMission reqUpdateMission) throws Exception {
        // given
        given(missionService.updateMissionInformation(any())).willReturn(1L);
        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(reqUpdateMission);
        // then
        mockMvc.perform(put(PATH)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

}
