package com.ly.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin2.server.internal.EnableZipkinServer;



/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class ZipkinApplication 
{
    public static void main( String[] args )
    {
    	args = new String[1];
        args[0] = "--spring.profiles.active=zipkin-http";    	
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
