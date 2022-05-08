package com.mission.dto.mission;

import com.mission.domain.Holiday;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RequestCreateMission {

    private Long missionId;
    @NotEmpty
    private String subject;
    @NotNull
    private Holiday holiday;
    @Min(value = 1)
    private int numberOfParticipants;
    @NotEmpty
    private String creator;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotEmpty
    private List<String> missionOfTopicInterests;

}
