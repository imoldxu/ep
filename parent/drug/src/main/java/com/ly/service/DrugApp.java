package com.ly.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCircuitBreaker
//@EnableHystrixDashboard
//@EnableFeignClients(basePackages="com.ly.service.feign.client")
@ComponentScan("com.ly.service.*")
public class DrugApp {

	public static void main(String[] args) {
		SpringApplication.run(DrugApp.class, args);
	}
}
