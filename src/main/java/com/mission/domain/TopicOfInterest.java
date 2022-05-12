package com.mission.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
@Entity @Getter
@Table(name = "topic_of_interest")
@AllArgsConstructor
@NoArgsConstructor
public class TopicOfInterest {

    @Id @GeneratedValue
    @Column(name = "topic_of_interest_id")
    private Long topicOfInterestId;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "topicOfInterest")
    private List<MemberOfTopicInterest> memberOfTopicInterests = new ArrayList<>();

    public void addMemberOfTopicInterest(MemberOfTopicInterest memberOfTopicInterest) {
        memberOfTopicInterests.add(memberOfTopicInterest);
        memberOfTopicInterest.setTopicOfInterest(this);
    }

    public TopicOfInterest(String name) {
        this.name = name;
    }

    public static List<TopicOfInterest> createTopicOfInterestName(List<String> names) {
        return names
          .stream()
          .map(TopicOfInterest::new)
          .collect(Collectors.toList());
    }

    public static List<TopicOfInterest> nonExistsTopic(List<String> requestMissionOfTopicInterestsNames, List<String> existsTopicOfInterests) {
        List<String> nonExistsTopic = requestMissionOfTopicInterestsNames
          .stream()
          .filter(origin -> existsTopicOfInterests
            .stream()
            .noneMatch(exists -> exists.equals(origin)))
          .collect(Collectors.toList());
        return createTopicOfInterestName(nonExistsTopic);
    }

}

