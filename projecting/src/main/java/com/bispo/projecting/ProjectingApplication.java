package com.bispo.projecting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProjectingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectingApplication.class, args);
		log.info("Swagger: http://localhost:8080/swagger-ui/index.html");
	}

}
