package com.mission.service;

import com.mission.domain.Holiday;
import com.mission.dto.mission.RequestCreateMission;

import java.time.LocalDateTime;
import java.util.List;

public class MissionTestData {

    private final static String SUBJECT = "미션";
    private final static Holiday HOLIDAY = new Holiday(true, true, true, true, true, true, true);
    private final static int NUMBER_OF_PARTICIPANTS = 1;
    private final static String CREATOR = "junwoo.choi";
    private final static LocalDateTime START_DATE = LocalDateTime.now();
    private final static LocalDateTime END_DATE = LocalDateTime.now().plusDays(7);
    private final static List<String> MISSION_OF_TOPIC_INTERESTS = List.of("Spring", "Devops");

    public static RequestCreateMission createMissionData() {
        return RequestCreateMission.builder()
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateSubjectData(Long missionId) {
        String subject = "미션1";

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(subject)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateHolidayData(Long missionId) {
        Holiday holiday = new Holiday(false, true, true, true, true, true, true);

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(holiday)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateNumberOfParticipantsData(Long missionId) {
        int numberOfParticipants = 2;

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(numberOfParticipants)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateCreatorData(Long missionId) {
        String creator = "sanghoon.lee";

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(creator)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateStartDateData(Long missionId) {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(startDate)
                .endDate(END_DATE)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateEndDateData(Long missionId) {
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(endDate)
                .missionOfTopicInterests(MISSION_OF_TOPIC_INTERESTS)
                .build();
    }

    public static RequestCreateMission updateMissionOfTopicInterestsData(Long missionId) {
        List<String> missionOfTopicInterests = List.of("Spring", "Devops", "React");

        return RequestCreateMission.builder()
                .missionId(missionId)
                .subject(SUBJECT)
                .holiday(HOLIDAY)
                .numberOfParticipants(NUMBER_OF_PARTICIPANTS)
                .creator(CREATOR)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .missionOfTopicInterests(missionOfTopicInterests)
                .build();
    }


}
