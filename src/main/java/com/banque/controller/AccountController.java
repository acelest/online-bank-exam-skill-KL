package com.banque.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banque.model.Account;
import com.banque.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@Tag(name = "Gestion des comptes", description = "API de gestion des comptes bancaires")
@SecurityRequirement(name = "basicAuth")
public class AccountController {

    private final AccountService accountService;
    
    @GetMapping
    @Operation(
        summary = "Liste des comptes", 
        description = "Récupère la liste des comptes de l'utilisateur connecté"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Liste des comptes récupérée"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Non autorisé")
    })
    public ResponseEntity<com.banque.dto.ApiResponse> listeComptes(Principal principal) {
        List<Account> comptes = accountService.findAllByUsername(principal.getName());
        return ResponseEntity.ok(new com.banque.dto.ApiResponse("SUCCES", "Liste des comptes récupérée", comptes));
    }
}
