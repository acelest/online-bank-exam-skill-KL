package com.banque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Banque en Ligne avec OTP")
                .version("1.0")
                .description("Documentation de l'API pour l'application de banque en ligne avec authentification par OTP")
                .contact(new Contact()
                    .name("Équipe de développement")
                    .email("contact@banque-enligne.com"))
                .license(new License()
                    .name("Licence propriétaire")
                    .url("https://banque-enligne.com/licence")))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
            .components(new Components()
                .addSecuritySchemes("basicAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("basic")
                    .description("Authentification HTTP Basic")));
    }
}
