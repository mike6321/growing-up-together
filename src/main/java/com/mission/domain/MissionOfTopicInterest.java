package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "mission_of_topic_of_interest")
@Getter
public class MissionOfTopicInterest {

    @Id @GeneratedValue
    @Column(name = "mission_of_topic_of_interest_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private TopicOfInterest topicOfInterest;
    // TODO 단방향
    //private Mission mission;

}
