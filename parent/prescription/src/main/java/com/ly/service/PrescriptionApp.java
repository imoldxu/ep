package com.ly.service;

import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTransactionManagement
@EnableFeignClients(basePackages="com.ly.service.feign.client")
@ComponentScan("com.ly.service.*")
@EnableRedissonHttpSession
public class PrescriptionApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(PrescriptionApp.class, args);
    }
}
