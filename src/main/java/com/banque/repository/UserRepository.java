package com.banque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banque.model.User;
import com.banque.model.User.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     * 
     * @param username le nom d'utilisateur à rechercher
     * @return l'utilisateur correspondant ou empty si aucun trouvé
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur et son statut
     * 
     * @param username le nom d'utilisateur à rechercher
     * @param status le statut de l'utilisateur
     * @return l'utilisateur correspondant ou empty si aucun trouvé
     */
    Optional<User> findByUsernameAndStatus(String username, UserStatus status);
    
    /**
     * Vérifie si un utilisateur existe avec le nom d'utilisateur spécifié
     * 
     * @param username le nom d'utilisateur à vérifier
     * @return true si un utilisateur existe avec ce nom, false sinon
     */
    boolean existsByUsername(String username);
    
    /**
     * Vérifie si un utilisateur existe avec l'email spécifié
     * 
     * @param email l'email à vérifier
     * @return true si un utilisateur existe avec cet email, false sinon
     */
    boolean existsByEmail(String email);
}
