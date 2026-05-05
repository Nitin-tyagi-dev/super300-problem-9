package com.cdrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CdrnApplication {
    public static void main(String[] args) {
        SpringApplication.run(CdrnApplication.class, args);
    }
}
