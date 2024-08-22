package com.keenable.user_magnagement_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EnableJpaRepositories(basePackages = "com.keenable.user_magnagement_application.repository")
public class UserMagnagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMagnagementApplication.class, args);
	}

}
