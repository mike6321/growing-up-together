package com.mission.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topic_of_interest")
@Getter
public class TopicOfInterest {

    @Id @GeneratedValue
    @Column(name = "topic_of_interest_id")
    private Long topicOfInterestId;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "topicOfInterest")
    private List<MemberOfTopicInterest> memberOfTopicInterests = new ArrayList();

    public void addMemberOfTopicInterest(MemberOfTopicInterest memberOfTopicInterest) {
        memberOfTopicInterests.add(memberOfTopicInterest);
        memberOfTopicInterest.setTopicOfInterest(this);
    }
}

