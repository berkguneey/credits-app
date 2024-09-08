package com.project.credits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreditsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditsApplication.class, args);
    }

}
