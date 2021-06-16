package com.fileshare.filepool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FilepoolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilepoolsApplication.class, args);
	}

}
