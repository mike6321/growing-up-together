package com.mission.domain;

import com.mission.dto.mission.ReqCreateMission;
import com.mission.dto.mission.ReqUpdateMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity @Table(name = "mission")
@DynamicUpdate
public class Mission {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;
    @Column(name = "subject")
    private String subject;
    @Builder.Default
    @Embedded
    private Holiday holiday = new Holiday();
    @Column(name = "number_of_participants")
    private int numberOfParticipants;
    @Column(name = "creator")
    private String creator;
    @Column(name = "startDate")
    private LocalDateTime startDate;
    @Column(name = "endDate")
    private LocalDateTime endDate;
    @Builder.Default
    @OneToMany(mappedBy = "mission")
    private List<MissionOfTopicInterest> missionOfTopicInterests = new ArrayList<>();

    public void createMission(ReqCreateMission reqCreateMission,
                              List<MissionOfTopicInterest> missionOfTopicInterests) {
        this.subject = reqCreateMission.getSubject();
        this.holiday = reqCreateMission.getHoliday();
        this.numberOfParticipants = reqCreateMission.getNumberOfParticipants();
        this.creator = reqCreateMission.getCreator();
        this.startDate = reqCreateMission.getStartDate();
        this.endDate = reqCreateMission.getEndDate();
        missionOfTopicInterests
                .stream()
                .forEach(this::addMissionOfTopicInterests);
    }

    public void addMissionOfTopicInterests(MissionOfTopicInterest missionOfTopicInterest) {
        this.missionOfTopicInterests.add(missionOfTopicInterest);
        missionOfTopicInterest.createMission(this);
    }

    public static Mission of(ReqUpdateMission reqUpdateMission) {
        return Mission
                .builder()
                .id(reqUpdateMission.getMissionId())
                .subject(reqUpdateMission.getSubject())
                .holiday(reqUpdateMission.getHoliday())
                .numberOfParticipants(reqUpdateMission.getNumberOfParticipants())
                .creator(reqUpdateMission.getCreator())
                .startDate(reqUpdateMission.getStartDate())
                .endDate(reqUpdateMission.getEndDate())
                .build();
    }

}
