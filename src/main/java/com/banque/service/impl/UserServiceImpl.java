package com.banque.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.exception.AuthenticationException;
import com.banque.exception.ResourceNotFoundException;
import com.banque.model.User;
import com.banque.repository.UserRepository;
import com.banque.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User inscrireUtilisateur(InscriptionRequest request) {
        log.info("Inscription d'un nouvel utilisateur: {}", request.getUsername());
        
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Nom d'utilisateur déjà utilisé: {}", request.getUsername());
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris");
        }
        
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email déjà utilisé: {}", request.getEmail());
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        
        // Créer l'utilisateur
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateInscription(LocalDateTime.now())
                .status(User.UserStatus.ACTIF)
                .build();
        
        // Sauvegarder l'utilisateur
        user = userRepository.save(user);
        log.info("Utilisateur créé avec succès: {}", user.getUsername());
        return user;
    }

    @Override
    public User authentifierUtilisateur(LoginRequest request) {
        log.info("Tentative d'authentification pour l'utilisateur: {}", request.getUsername());
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Utilisateur non trouvé: {}", request.getUsername());
                    return new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect");
                });
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Tentative de connexion échouée pour l'utilisateur: {}", request.getUsername());
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect");
        }
        
        log.info("Utilisateur authentifié avec succès: {}", user.getUsername());
        return user;
    }

    @Override
    public User recupererParUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public boolean existeParUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existeParEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
