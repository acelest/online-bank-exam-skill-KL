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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class ApiAuthController {

    private final UserService userService;
    
    @PostMapping("/inscription")
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
