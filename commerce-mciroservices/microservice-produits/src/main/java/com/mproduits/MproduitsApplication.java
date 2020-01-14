package com.mproduits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties
@EnableEurekaClient

public class MproduitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MproduitsApplication.class, args);
	}
}