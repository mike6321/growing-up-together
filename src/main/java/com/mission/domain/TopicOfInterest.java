package com.mission.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "topic_of_interest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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

