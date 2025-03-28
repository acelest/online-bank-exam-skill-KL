package com.banque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banque.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Recherche un utilisateur par son nom d'utilisateur
     * 
     * @param username le nom d'utilisateur à rechercher
     * @return l'utilisateur trouvé ou empty si aucun utilisateur n'est trouvé
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Vérifie si un utilisateur avec le nom d'utilisateur donné existe
     * 
     * @param username le nom d'utilisateur à vérifier
     * @return true si un utilisateur avec ce nom existe, false sinon
     */
    boolean existsByUsername(String username);
    
    /**
     * Vérifie si un utilisateur avec l'email donné existe
     * 
     * @param email l'email à vérifier
     * @return true si un utilisateur avec cet email existe, false sinon
     */
    boolean existsByEmail(String email);
}
