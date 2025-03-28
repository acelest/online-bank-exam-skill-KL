package com.banque.service;

import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.model.User;

/**
 * Service responsable de la gestion des utilisateurs
 */
public interface UserService {
    
    /**
     * Inscrit un nouvel utilisateur dans le système
     * 
     * @param request les informations d'inscription
     * @return l'utilisateur créé
     * @throws IllegalArgumentException si le nom d'utilisateur ou l'email existe déjà
     */
    User inscrireUtilisateur(InscriptionRequest request);
    
    /**
     * Authentifie un utilisateur
     * 
     * @param request les informations de connexion
     * @return l'utilisateur authentifié
     * @throws com.banque.exception.AuthenticationException si l'authentification échoue
     */
    User authentifierUtilisateur(LoginRequest request);
    
    /**
     * Récupère un utilisateur par son nom d'utilisateur
     * 
     * @param username le nom d'utilisateur
     * @return l'utilisateur correspondant
     * @throws com.banque.exception.ResourceNotFoundException si l'utilisateur n'existe pas
     */
    User recupererParUsername(String username);
    
    /**
     * Vérifie si un nom d'utilisateur existe déjà
     * 
     * @param username le nom d'utilisateur à vérifier
     * @return true si le nom d'utilisateur existe, false sinon
     */
    boolean existeParUsername(String username);
    
    /**
     * Vérifie si un email existe déjà
     * 
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean existeParEmail(String email);
}
