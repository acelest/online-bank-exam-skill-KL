package com.banque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banque.model.Account;
import com.banque.model.AccountType;
import com.banque.model.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /**
     * Trouve tous les comptes appartenant à un utilisateur
     * 
     * @param user l'utilisateur propriétaire des comptes
     * @return la liste des comptes de l'utilisateur
     */
    List<Account> findByUser(User user);
    
    /**
     * Vérifie si un compte existe avec le numéro de compte spécifié
     * 
     * @param accountNumber le numéro de compte à vérifier
     * @return true si un compte existe avec ce numéro, false sinon
     */
    boolean existsByAccountNumber(String accountNumber);
}
