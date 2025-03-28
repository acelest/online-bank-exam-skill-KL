package com.banque.repository;

import com.banque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Recherche un utilisateur par son nom d'utilisateur
     * @param username le nom d'utilisateur
     * @return l'utilisateur correspondant, s'il existe
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Recherche un utilisateur par son adresse email
     * @param email l'adresse email
     * @return l'utilisateur correspondant, s'il existe
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Vérifie si un utilisateur existe avec le nom d'utilisateur donné
     * @param username le nom d'utilisateur
     * @return true si un utilisateur avec ce nom existe, false sinon
     */
    boolean existsByUsername(String username);
    
    /**
     * Vérifie si un utilisateur existe avec l'adresse email donnée
     * @param email l'adresse email
     * @return true si un utilisateur avec cette email existe, false sinon
     */
    boolean existsByEmail(String email);
    
    /**
     * Recherche un utilisateur par son nom d'utilisateur et son statut
     * @param username le nom d'utilisateur
     * @param status le statut de l'utilisateur
     * @return l'utilisateur correspondant, s'il existe
     */
    Optional<User> findByUsernameAndStatus(String username, User.UserStatus status);
}
