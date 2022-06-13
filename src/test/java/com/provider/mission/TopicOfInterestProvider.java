package com.provider.mission;

import com.mission.domain.TopicOfInterest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class TopicOfInterestProvider {

    public static final String PROVIDER_CLASSPATH = "com.provider.mission.TopicOfInterestProvider#";

    static Stream<Arguments> topicOfInterestListProvider() {
        TopicOfInterest topicOfInterest1 = new TopicOfInterest();
        TopicOfInterest topicOfInterest2 = new TopicOfInterest();
        List<TopicOfInterest> topicOfInterests = List.of(topicOfInterest1, topicOfInterest2);
        return Stream.of(Arguments.arguments(topicOfInterests));
    }

}
