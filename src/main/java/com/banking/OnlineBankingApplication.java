package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.banking.model")
@EnableJpaRepositories("com.banking.repository")
public class OnlineBankingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OnlineBankingApplication.class, args);
    }
}
