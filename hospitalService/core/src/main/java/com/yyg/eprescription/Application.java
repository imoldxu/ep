package com.yyg.eprescription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.yyg.eprescription.*")
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
