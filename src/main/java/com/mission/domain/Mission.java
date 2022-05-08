package com.mission.domain;

import com.mission.dto.mission.RequestCreateMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Entity @Table(name = "mission")
public class Mission {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;
    @Column(name = "subject")
    private String subject;
    @Embedded
    private Holiday holiday;
    @Column(name = "numberOfParticipants")
    private int numberOfParticipants;
    @Column(name = "creator")
    private String creator;
    @Column(name = "startDate")
    private LocalDateTime startDate;
    @Column(name = "endDate")
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MissionOfTopicInterest> missionOfTopicInterests = new ArrayList<>();

    public static Mission createMission(RequestCreateMission requestCreateMission, List<MissionOfTopicInterest> missionOfTopicInterests) {
        Mission mission = new Mission();
        mission.updateSubject(requestCreateMission.getSubject());
        mission.updateHoliday(requestCreateMission.getHoliday());
        mission.updateNumberOfParticipants(requestCreateMission.getNumberOfParticipants());
        mission.updateCreator(requestCreateMission.getCreator());
        mission.updateStartDate(requestCreateMission.getStartDate());
        mission.updateEndDate(requestCreateMission.getEndDate());
        missionOfTopicInterests.stream()
                               .forEach(mission::addMissionOfTopicInterests);
        return mission;
    }

    public void addMissionOfTopicInterests(MissionOfTopicInterest missionOfTopicInterest) {
        this.missionOfTopicInterests.add(missionOfTopicInterest);
        missionOfTopicInterest.createMission(this);
    }

    public void updateSubject(String subject) {
        this.subject = subject;
    }

    public void updateHoliday(Holiday holiday) {
        this.holiday = holiday;
    }

    public void updateNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void updateCreator(String creator) {
        this.creator = creator;
    }

    public void updateStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
