package com.capgemeni.railwayAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RailwayAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayAuthenticationApplication.class, args);
	}

}
