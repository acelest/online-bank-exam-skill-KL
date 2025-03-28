package com.banque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Authentification", description = "API d'authentification et de gestion des sessions")
public class LoginController {

    @GetMapping("/login")
    @Operation(summary = "Page de connexion", description = "Affiche la page de connexion à l'application")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/")
    @Operation(summary = "Page d'accueil", description = "Affiche la page d'accueil de l'application")
    public String homePage() {
        return "home";
    }
}
