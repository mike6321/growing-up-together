package com.mission.controller;

import com.annotation.WithAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mission.domain.Holiday;
import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import com.mission.service.MissionService;
import com.mission.service.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MissionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MissionService missionService;

    @DisplayName("미션 생성 컨트롤러 테스트")
    @ParameterizedTest
    @MethodSource("requestMissionCreateProvider")
    @WithAccount(email = "test_account@test.com", nickname = "test_account")
    void create_mission_test(ReqCreateMission reqCreateMission) throws Exception {
        given(missionService.saveMission(any())).willReturn(1L);
        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(reqCreateMission);
        mockMvc.perform(post("/mission")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @DisplayName("미션 수정 컨트롤러 테스트")
    @ParameterizedTest
    @MethodSource("requestMissionUpdateProvider")
    @WithAccount(email = "test_account@test.com", nickname = "test_account")
    void update_mission_test(ReqUpdateMission reqUpdateMission) throws Exception {
        given(missionService.updateMissionInformation(any())).willReturn(1L);
        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(reqUpdateMission);
        mockMvc.perform(put("/mission")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    static Stream<Arguments> requestMissionCreateProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return Stream.of(Arguments.of(
                ReqCreateMission
                        .builder()
                        .subject("subject")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(1)
                        .creator("junwoo.choi@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

    static Stream<Arguments> requestMissionUpdateProvider() {
        List<String> missionOfTopicInterests = List.of("spring", "kafka", "vue.js");
        return Stream.of(Arguments.of(
                ReqUpdateMission
                        .builder()
                        .missionId(1L)
                        .subject("subject")
                        .holiday(new Holiday(false, true, true, true, true, true, true))
                        .numberOfParticipants(1)
                        .creator("junwoo.choi@test.com")
                        .startDate(Utils.parseDate("2022/05/14"))
                        .endDate(Utils.parseDate("2022/05/21"))
                        .missionOfTopicInterests(missionOfTopicInterests)
                        .build()
        ));
    }

}
