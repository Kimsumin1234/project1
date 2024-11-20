package com.example.project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling // @Scheduled 사용하기 위해 필요
@SpringBootApplication
public class Project1Application {

  public static void main(String[] args) {
    SpringApplication.run(Project1Application.class, args);
  }
}
