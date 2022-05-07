package com.mission.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mission_of_topic_of_interest")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MissionOfTopicInterest {

    @Id @GeneratedValue
    @Column(name = "mission_of_topic_of_interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "topic_of_interest_id"),
            @JoinColumn(name = "name")
    })
    private TopicOfInterest topicOfInterest;

}
