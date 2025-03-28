package com.banque.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public User inscrireUtilisateur(InscriptionRequest request) {
        log.info("Inscription d'un nouvel utilisateur: {}", request.getUsername());
        
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Nom d'utilisateur déjà utilisé: {}", request.getUsername());
            throw new AuthenticationException("Ce nom d'utilisateur est déjà utilisé");
        }
        
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Email déjà utilisé: {}", request.getEmail());
            throw new AuthenticationException("Cet email est déjà utilisé");
        }
        
        // Créer un nouvel utilisateur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setStatus(User.UserStatus.ACTIF);
        
        // Sauvegarder l'utilisateur dans la base de données
        return userRepository.save(user);
    }
    
    @Override
    public User authentifierUtilisateur(LoginRequest request) {
        log.info("Tentative d'authentification pour l'utilisateur: {}", request.getUsername());
        
        // Récupérer l'utilisateur par son nom d'utilisateur
        User user = userRepository.findByUsernameAndStatus(request.getUsername(), User.UserStatus.ACTIF)
                .orElseThrow(() -> {
                    log.error("Utilisateur non trouvé ou inactif: {}", request.getUsername());
                    return new AuthenticationException("Identifiants invalides");
                });
        
        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Mot de passe incorrect pour l'utilisateur: {}", request.getUsername());
            throw new AuthenticationException("Identifiants invalides");
        }
        
        log.info("Authentification réussie pour l'utilisateur: {}", request.getUsername());
        return user;
    }
    
    @Override
    public User recupererParUsername(String username) {
        log.info("Récupération de l'utilisateur par username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Utilisateur non trouvé: {}", username);
                    return new ResourceNotFoundException("Utilisateur non trouvé");
                });
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
