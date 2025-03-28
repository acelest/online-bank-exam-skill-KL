package com.banque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banque.dto.ApiResponse;
import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.dto.UserDTO;
import com.banque.exception.AuthenticationException;
import com.banque.model.User;
import com.banque.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentification", description = "API pour l'inscription et la connexion des utilisateurs")
public class ApiAuthController {

    private final UserService userService;
    
    @PostMapping("/inscription")
    @Operation(summary = "Inscription d'un nouvel utilisateur", 
              description = "Permet à un nouvel utilisateur de s'inscrire dans le système")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscription réussie",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Données d'inscription invalides",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<UserDTO>> inscription(@Valid @RequestBody InscriptionRequest request) {
        log.info("Inscription d'un nouvel utilisateur: {}", request.getUsername());
        
        try {
            User user = userService.inscrireUtilisateur(request);
            UserDTO userDTO = convertToDTO(user);
            
            return ResponseEntity.ok(new ApiResponse<>("SUCCES", "Inscription réussie", userDTO));
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de l'inscription: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("ERREUR", e.getMessage(), null));
        }
    }
    
    @PostMapping("/connexion")
    @Operation(summary = "Connexion d'un utilisateur", 
              description = "Authentifie un utilisateur avec son nom d'utilisateur et mot de passe")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Connexion réussie",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Authentification échouée",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<UserDTO>> connexion(@Valid @RequestBody LoginRequest request) {
        log.info("Tentative de connexion pour l'utilisateur: {}", request.getUsername());
        
        try {
            User user = userService.authentifierUtilisateur(request);
            UserDTO userDTO = convertToDTO(user);
            
            return ResponseEntity.ok(new ApiResponse<>("SUCCES", "Connexion réussie", userDTO));
        } catch (AuthenticationException e) {
            log.error("Erreur lors de la connexion: {}", e.getMessage());
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>("ERREUR", e.getMessage(), null));
        }
    }
    
    /**
     * Convertit un utilisateur en DTO pour ne pas exposer de données sensibles
     * 
     * @param user l'utilisateur à convertir
     * @return le DTO correspondant
     */
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build();
    }
}
