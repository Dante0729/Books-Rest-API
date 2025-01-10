package com.portfolio.davidreyes.booksapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Books API application.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class DemoApplication {

	/**
	 * The main method that starts the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}
}
