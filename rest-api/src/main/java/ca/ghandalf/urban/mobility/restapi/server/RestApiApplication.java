package ca.ghandalf.urban.mobility.restapi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// https://docs.spring.io/spring-boot/docs/2.1.0.M2/reference/html/
@SpringBootApplication//(scanBasePackages = {"ca.ghandalf.urban.mobility"})
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
		
	}

}
