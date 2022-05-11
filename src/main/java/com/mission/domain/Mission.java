package com.mission.domain;

import com.mission.dto.mission.RequestCreateMission;
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
@AllArgsConstructor(staticName = "of")
@Builder
@Entity @Table(name = "mission")
@DynamicUpdate
public class Mission {

    @Id @GeneratedValue
    @Column(name = "mission_id")
    private Long id;
    @Column(name = "subject")
    private String subject;
    @Embedded
    private Holiday holiday = new Holiday();
    @Column(name = "numberOfParticipants")
    private int numberOfParticipants;
    @Column(name = "creator")
    private String creator;
    @Column(name = "startDate")
    private LocalDateTime startDate;
    @Column(name = "endDate")
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "mission")
    private List<MissionOfTopicInterest> missionOfTopicInterests = new ArrayList<>();

    public void createMission(RequestCreateMission requestCreateMission, List<MissionOfTopicInterest> missionOfTopicInterests) {
        this.subject = requestCreateMission.getSubject();
        this.holiday = requestCreateMission.getHoliday();
        this.numberOfParticipants = requestCreateMission.getNumberOfParticipants();
        this.creator = requestCreateMission.getCreator();
        this.startDate = requestCreateMission.getStartDate();
        this.endDate = requestCreateMission.getEndDate();
        missionOfTopicInterests.stream()
                               .forEach(this::addMissionOfTopicInterests);
    }

    public void addMissionOfTopicInterests(MissionOfTopicInterest missionOfTopicInterest) {
        this.missionOfTopicInterests.add(missionOfTopicInterest);
        missionOfTopicInterest.createMission(this);
    }

}
