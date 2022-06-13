package com.mission.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TopicOfInterestTest {

    private List<String> topicNames;
    private List<String> existsTopicNames;

    @BeforeEach
    void initTopicNames() {
        topicNames = List.of("Spring", "Reacitve Programming", "Vue.js");
        existsTopicNames = List.of("Reacitve Programming", "Vue.js");
    }

    @DisplayName("관심주제이름 리스트 TopicOfInterest 인스턴스 변환 테스트")
    @Test
    void create_topic_of_interestName_test() {
        // when
        List<TopicOfInterest> topicOfInterest = TopicOfInterest.createTopicOfInterestName(topicNames);
        // then
        assertThat(topicOfInterest.size()).isEqualTo(3);
        IntStream.range(0, topicNames.size())
                 .forEach(i -> assertThat(topicOfInterest.get(i).getName()).isEqualTo(topicNames.get(i)));
    }

    @DisplayName("관심주제이름 리스트 중 이미 등록 된 관심주제이름 제외 테스트")
    @Test
    void non_exists_topic_test() {
        // when
        List<TopicOfInterest> topicOfInterests = TopicOfInterest.nonExistsTopic(topicNames, existsTopicNames);
        // then
        assertThat(topicOfInterests.size()).isEqualTo(1);
    }

}
