package com.waes.passport.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application Class responsible to define Spring Boot configurations
 * @author tonsaito
 */
@SpringBootApplication
@ComponentScan("com.waes.passport")
@EntityScan("com.waes.passport.entity")
@EnableJpaRepositories("com.waes.passport.repository")
@EnableTransactionManagement
public class Application {
	
	public static void main(String ... args) {
		SpringApplication.run(Application.class, args);
	}
}