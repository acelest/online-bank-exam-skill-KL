package com.banque.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Cette classe désactive complètement Spring Security
 * quand spring.security.enabled=false dans application.properties
 */
@Configuration
@ConditionalOnProperty(name = "spring.security.enabled", havingValue = "false")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class DisableSecurityConfig {
    // Aucun code nécessaire - la désactivation est faite par les annotations
}
