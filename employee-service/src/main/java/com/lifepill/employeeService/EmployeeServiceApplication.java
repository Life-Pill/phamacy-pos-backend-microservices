package com.lifepill.employeeService;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info = @Info(
				title = "LifePill POS - Employee management microservices REST API Documentation",
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
				description = "LifePill POS System Employee microservice REST API Documentation",
				url = "http://localhost:8084/swagger-ui/index.html#/"
		)
)
@SpringBootApplication
@EnableFeignClients
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}