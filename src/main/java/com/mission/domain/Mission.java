package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission")
@Getter
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_of_topic_of_interest_id")
    private List<MissionOfTopicInterest> missionOfTopicInterests = new ArrayList<>();

}
