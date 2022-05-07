package com.mission.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "member_of_topic_interest")
@Getter
public class MemberOfTopicInterest {

    @Id @GeneratedValue
    @Column(name = "member_of_topic_of_interest_id")
    private Long memberOfTopicOfInterestId;
    @ManyToOne(fetch = FetchType.LAZY)
    private TopicOfInterest topicOfInterest;

}
