package com.banque.service;

import com.banque.dto.InscriptionRequest;
import com.banque.dto.LoginRequest;
import com.banque.model.User;

public interface UserService {
    
    /**
     * Inscrire un nouvel utilisateur dans le système
     * @param request les informations d'inscription
     * @return l'utilisateur créé
     */
    User inscrireUtilisateur(InscriptionRequest request);
    
    /**
     * Authentifier un utilisateur avec ses identifiants
     * @param request les informations de connexion
     * @return l'utilisateur authentifié
     */
    User authentifierUtilisateur(LoginRequest request);
    
    /**
     * Récupérer un utilisateur par son nom d'utilisateur
     * @param username le nom d'utilisateur
     * @return l'utilisateur correspondant
     */
    User recupererParUsername(String username);
    
    /**
     * Vérifier si un nom d'utilisateur existe déjà
     * @param username le nom d'utilisateur à vérifier
     * @return true si le nom d'utilisateur existe, false sinon
     */
    boolean existeParUsername(String username);
    
    /**
     * Vérifier si un email existe déjà
     * @param email l'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean existeParEmail(String email);
}
