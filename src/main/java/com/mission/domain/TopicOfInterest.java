package com.mission.domain;


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
@NoArgsConstructor
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

    public TopicOfInterest(String name) {
        this.name = name;
    }

    public static List<TopicOfInterest> createTopicOfInterestName(List<String> names) {
        return IntStream.range(0, names.size())
                        .mapToObj(i -> new TopicOfInterest(names.get(i)))
                        .collect(Collectors.toList());
    }

    public static List<TopicOfInterest> nonExistsTopic(List<String> requestMissionOfTopicInterestsNames, List<String> existsTopicOfInterests) {
        List<String> nonExistsTopic = requestMissionOfTopicInterestsNames.stream()
                                                                         .filter(origin -> existsTopicOfInterests.stream()
                                                                                                                 .noneMatch(exists -> exists.equals(origin)))
                                                                         .collect(Collectors.toList());
        return createTopicOfInterestName(nonExistsTopic);
    }

}

