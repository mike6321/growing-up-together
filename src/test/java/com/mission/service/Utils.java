package com.mission.service;

import com.mission.domain.Holiday;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static LocalDateTime parseDate(String target) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withNano(1);
    }

    public static Holiday getHoliday(ArgumentsAccessor accessor, int index) {
        String holidayValue = accessor.getString(index);
        String[] splitHolidayValue = holidayValue.trim().split("\\s+");
        return new Holiday(
                Boolean.parseBoolean(splitHolidayValue[0]),
                Boolean.parseBoolean(splitHolidayValue[1]),
                Boolean.parseBoolean(splitHolidayValue[2]),
                Boolean.parseBoolean(splitHolidayValue[3]),
                Boolean.parseBoolean(splitHolidayValue[4]),
                Boolean.parseBoolean(splitHolidayValue[5]),
                Boolean.parseBoolean(splitHolidayValue[6])
        );
    }

    public static List<String> getMissionOfTopicInterests(ArgumentsAccessor accessor, int index) {
        String missionList = accessor.getString(index);
        return Arrays.stream(missionList.trim().split("\\s+"))
                .collect(Collectors.toList());
    }

}
