package com.mission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionOfTopicInterest {

    @Id @GeneratedValue
    @Column(name = "mission_of_topic_interest_id")
    private Long id;
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_of_interest_id")
    private TopicOfInterest topicOfInterest;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    public MissionOfTopicInterest(TopicOfInterest topicOfInterest) {
        this.topicOfInterest = topicOfInterest;
    }

    public void createMission(Mission mission) {
        this.mission = mission;
    }

}
