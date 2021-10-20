package com.capgemeni.railwayBooking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableSwagger2
public class RailwayBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailwayBookingApplication.class, args);
	}
	
	//To communicate between microservices RestTempalte bean needs to create
	@Bean
	RestTemplate restTemplate(){
	    return new RestTemplate();
	  }
	@Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.capgemeni.railwayBooking.Controller")).build();
	   }
	
	

}
