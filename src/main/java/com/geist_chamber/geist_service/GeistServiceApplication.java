package com.geist_chamber.geist_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GeistServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeistServiceApplication.class, args);
	}

}
