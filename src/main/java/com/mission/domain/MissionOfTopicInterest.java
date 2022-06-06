package com.mission.domain;

import lombok.*;

import javax.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mission_of_topic_of_interest")
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

    public static List<MissionOfTopicInterest> of(List<TopicOfInterest> topicOfInterests) {
        return topicOfInterests
                .stream()
                .map(MissionOfTopicInterest::new)
                .collect(Collectors.toList());
    }

}
