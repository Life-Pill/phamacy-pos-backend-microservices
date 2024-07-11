package com.lifepill.authService;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@OpenAPIDefinition(
		info = @Info(
				title = "LifePill Auth-System API Documentation",
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
				description = "LifePill Auth Service System REST API Documentation",
				url = "http://localhost:8090/swagger-ui/index.html#/"
		)
)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl") // Enable JPA Auditing
@EnableEurekaClient
@EnableFeignClients
public class AuthServiceApplication {

	/**
	 * Entry point of the application.
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}

