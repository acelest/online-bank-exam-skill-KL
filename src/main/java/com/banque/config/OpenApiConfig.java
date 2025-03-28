package com.banque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicAuth", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                .info(new Info()
                        .title("API Banque en ligne")
                        .description("Documentation de l'API de la banque en ligne avec sécurité OTP")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Équipe de développement")
                                .email("contact@banque.com"))
                        .license(new License()
                                .name("Licence API")
                                .url("https://www.banque.com/api-licence")));
    }
}
