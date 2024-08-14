package com.example.eukeraregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EukeraregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EukeraregistryApplication.class, args);
	}

}
