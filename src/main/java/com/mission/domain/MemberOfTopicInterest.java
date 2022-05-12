package com.mission.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "member_of_topic_of_interest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberOfTopicInterest {

    @Id @GeneratedValue
    @Column(name = "member_of_topic_of_interest_id")
    private Long memberOfTopicOfInterestId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_of_interest_id")
    private TopicOfInterest topicOfInterest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setTopicOfInterest(TopicOfInterest topicOfInterest) {
        this.topicOfInterest = topicOfInterest;
    }

}
