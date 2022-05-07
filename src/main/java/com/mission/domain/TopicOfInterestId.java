package com.mission.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicOfInterestId implements Serializable {

    private Long topicOfInterestId;
    private String name;

}
