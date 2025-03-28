package com.banque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.model.User;
import com.banque.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "API Authentification", description = "API d'inscription et de connexion des utilisateurs")
public class ApiAuthController {

    private final UserService userService;
    
    @PostMapping("/inscription")
    @Operation(
        summary = "Inscription d'un nouvel utilisateur", 
        description = "Permet d'enregistrer un nouvel utilisateur dans le système"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscription réussie"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Données d'inscription invalides")
    })
    public ResponseEntity<com.banque.dto.ApiResponse> inscription(
        @Valid @RequestBody InscriptionRequest request
    ) {
        User user = userService.inscrireUtilisateur(request);
        return ResponseEntity.ok(new com.banque.dto.ApiResponse("SUCCES", "Inscription réussie", user.getUsername()));
    }
    
    @PostMapping("/connexion")
    @Operation(
        summary = "Connexion d'un utilisateur", 
        description = "Authentifie un utilisateur et renvoie ses informations de base"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Connexion réussie"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<com.banque.dto.ApiResponse> connexion(
        @Valid @RequestBody LoginRequest request
    ) {
        User user = userService.authentifierUtilisateur(request);
        return ResponseEntity.ok(new com.banque.dto.ApiResponse("SUCCES", "Connexion réussie", user.getUsername()));
    }
}
