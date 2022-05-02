package com.mission.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "topic_of_interest")
@Getter
public class TopicOfInterest {

    @Id @GeneratedValue
    @Column(name = "topic_of_interest_id")
    private Long topicOfInterestId;
    @Column(name = "name")
    private String name;

}
