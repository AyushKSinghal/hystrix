package com.expedia.servers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test: http://localhost:8080/delay
 * AND : http://localhost:8080/brittle
 * AND : http://localhost:8080/hystrix/
 */

@SpringBootApplication
@RestController
@EnableHystrixDashboard
public class ServersApplication {

	public static void main(String[] args) {
		System.out.println("Starting...");
		SpringApplication.run(ServersApplication.class, args);
	}

	@RequestMapping(method = {RequestMethod.GET}, value = {"/home"})
	public String home() {
		return "Hello from Server.";
	}
}
