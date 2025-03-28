package com.banque.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banque.dto.ApiResponse;
import com.banque.exception.ResourceNotFoundException;
import com.banque.model.Account;
import com.banque.model.Account.AccountType;
import com.banque.model.User;
import com.banque.service.AccountService;
import com.banque.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getMesComptes(Authentication authentication) {
        String username = authentication.getName();
        log.info("Récupération des comptes pour l'utilisateur: {}", username);
        
        List<Account> comptes = accountService.findAllByUsername(username);
        
        return ResponseEntity.ok(new ApiResponse<>(
                "SUCCES", 
                "Comptes récupérés avec succès", 
                comptes));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> getCompteById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        log.info("Récupération du compte {} pour l'utilisateur: {}", id, username);
        
        try {
            Account compte = accountService.findById(id);
            
            // Vérifier que le compte appartient bien à l'utilisateur connecté
            if (!compte.getUser().getUsername().equals(username)) {
                log.warn("Tentative d'accès non autorisé au compte {} par {}", id, username);
                return ResponseEntity.status(403)
                        .body(new ApiResponse<>("ERREUR", "Accès non autorisé", null));
            }
            
            return ResponseEntity.ok(new ApiResponse<>(
                    "SUCCES", 
                    "Compte récupéré avec succès", 
                    compte));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>("ERREUR", e.getMessage(), null));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Account>> creerCompte(
            @Valid @RequestBody String accountTypeStr, 
            Authentication authentication) {
        
        String username = authentication.getName();
        log.info("Création d'un compte {} pour l'utilisateur: {}", accountTypeStr, username);
        
        try {
            User user = userService.recupererParUsername(username);
            AccountType type = AccountType.valueOf(accountTypeStr.toUpperCase());
            
            Account nouveauCompte = accountService.createAccount(user, type);
            
            return ResponseEntity.ok(new ApiResponse<>(
                    "SUCCES", 
                    "Compte créé avec succès", 
                    nouveauCompte));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("ERREUR", "Type de compte invalide", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>("ERREUR", e.getMessage(), null));
        }
    }
}
