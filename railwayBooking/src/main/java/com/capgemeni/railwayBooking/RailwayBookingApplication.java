package com.capgemeni.railwayBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RailwayBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayBookingApplication.class, args);
	}

}
