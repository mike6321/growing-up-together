package com.mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GrowingUpTogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrowingUpTogetherApplication.class, args);
    }

}
