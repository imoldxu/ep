package com.yyg.pzrmyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.*")
@Configuration
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
