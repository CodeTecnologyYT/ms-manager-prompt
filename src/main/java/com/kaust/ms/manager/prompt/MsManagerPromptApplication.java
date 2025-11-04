package com.kaust.ms.manager.prompt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@PropertySource("classpath:database.properties")
@PropertySource("classpath:mail.properties")
@PropertySource("classpath:firebase.properties")
public class MsManagerPromptApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsManagerPromptApplication.class, args);
    }

}
