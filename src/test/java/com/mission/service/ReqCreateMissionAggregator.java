package com.mission.service;

import com.mission.domain.Holiday;
import com.mission.dto.mission.ReqUpdateMission;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class ReqCreateMissionAggregator implements ArgumentsAggregator {

    @SneakyThrows
    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        Holiday holiday = Utils.getHoliday(accessor, 1);
        return ReqUpdateMission.builder()
                .subject(accessor.getString(0))
                .holiday(holiday)
                .numberOfParticipants(accessor.getInteger(2))
                .creator(accessor.getString(3))
                .startDate(Utils.parseDate(accessor.getString(4)))
                .endDate(Utils.parseDate(accessor.getString(5)))
                .missionOfTopicInterests(Utils.getMissionOfTopicInterests(accessor, 6))
                .build();
    }

}
