package com.ly.service;

import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCircuitBreaker
//@EnableHystrixDashboard
//@EnableFeignClients(basePackages="com.ly.service.feign.client")
@EnableTransactionManagement
@EnableScheduling
@ComponentScan("com.ly.service.*")
@EnableRedissonHttpSession
public class SystemApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(SystemApp.class, args);
    }
}

