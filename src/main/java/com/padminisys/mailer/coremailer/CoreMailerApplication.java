package com.padminisys.mailer.coremailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CoreMailerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreMailerApplication.class, args);
    }

}
