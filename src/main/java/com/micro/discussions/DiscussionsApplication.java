package com.micro.discussions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DiscussionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscussionsApplication.class, args);
    }

}
