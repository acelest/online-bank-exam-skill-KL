package com.userfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UserFrontApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// Set system property for compatibility with newer Java versions
		System.setProperty("spring.main.allow-bean-definition-overriding", "true");
		
		SpringApplication.run(UserFrontApplication.class, args);
	}
}
