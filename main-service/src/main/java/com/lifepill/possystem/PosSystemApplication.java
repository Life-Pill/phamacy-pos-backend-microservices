package com.lifepill.possystem;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info = @Info(
				title = "LifePill POS -System API Documentation",
				description = "LIFEPILL",
				version = "v1",
				contact = @Contact(
						name = "LifePIll",
						email = "lifepillinfo@gmail.com",
						url = "https://github.com/Life-Pill"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/Life-Pill"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "LifePill POS System REST API Documentation",
				url = "http://localhost:8081/swagger-ui/index.html#/"
		)
)
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl") // Enable JPA Auditing
public class PosSystemApplication {

	/**
	 * Entry point of the application.
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PosSystemApplication.class, args);
	}

	/*@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}*/
}

