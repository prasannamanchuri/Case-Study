package com.capgemeni.railwayServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RailwayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayServerApplication.class, args);
	}

}
