package com.mission.config;

import com.mission.repository.MissionRepository;
import com.validation.CreateMissionValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {

    @Bean
    public CreateMissionValidator createMissionValidator(MissionRepository missionRepository) {
        return new CreateMissionValidator(missionRepository);
    }

}
