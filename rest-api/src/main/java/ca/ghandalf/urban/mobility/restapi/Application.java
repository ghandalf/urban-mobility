package ca.ghandalf.urban.mobility.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "ca.ghandalf" })
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "ca.ghandalf.urban.mobility" })
@EntityScan("ca.ghandalf.urban.mobility.domain")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
