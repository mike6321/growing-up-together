package com.mission.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "topic_of_interest")
@Getter
@NoArgsConstructor
@IdClass(TopicOfInterestId.class)
public class TopicOfInterest {

    @Id @GeneratedValue
    @Column(name = "topic_of_interest_id")
    private Long topicOfInterestId;
    @Column(name = "name", unique = true)
    @Id
    private String name;

    public TopicOfInterest(String name) {
        this.name = name;
    }

    public static List<TopicOfInterest> createTopicOfInterestName(List<String> names) {
        return IntStream.range(0, names.size())
                        .mapToObj(i -> new TopicOfInterest(names.get(i)))
                        .collect(Collectors.toList());
    }

}
