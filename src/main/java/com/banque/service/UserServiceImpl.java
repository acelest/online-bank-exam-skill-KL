package com.banque.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.exception.AuthenticationException;
import com.banque.model.User;
import com.banque.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Inscrit un nouvel utilisateur dans le système
     * 
     * @param request les informations d'inscription
     * @return l'utilisateur créé
     * @throws IllegalArgumentException si le nom d'utilisateur ou l'email existe déjà
     */
    @Transactional
    @Override
    public User inscrireUtilisateur(InscriptionRequest request) {
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris");
        }
        
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
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
                .build();
        
        return userRepository.save(user);
    }
    
    /**
     * Authentifie un utilisateur
     * 
     * @param request les informations de connexion
     * @return l'utilisateur authentifié
     * @throws AuthenticationException si l'authentification échoue
     */
    @Override
    public User authentifierUtilisateur(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Tentative de connexion échouée pour l'utilisateur: {}", request.getUsername());
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect");
        }
        
        log.info("Utilisateur authentifié avec succès: {}", user.getUsername());
        return user;
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur
     * 
     * @param username le nom d'utilisateur
     * @return l'utilisateur correspondant
     * @throws com.banque.exception.ResourceNotFoundException si l'utilisateur n'existe pas
     */
    @Override
    public User recupererParUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new com.banque.exception.ResourceNotFoundException("Utilisateur non trouvé"));
    }

    /**
     * Vérifie si un nom d'utilisateur existe déjà
     * 
     * @param username le nom d'utilisateur à vérifier
     * @return true si le nom d'utilisateur existe, false sinon
     */
    @Override
    public boolean existeParUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Vérifie si un email existe déjà
     * 
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    @Override
    public boolean existeParEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
