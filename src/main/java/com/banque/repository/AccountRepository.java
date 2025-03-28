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
     * Récupère tous les comptes d'un utilisateur spécifique
     * @param user l'utilisateur
     * @return la liste des comptes de l'utilisateur
     */
    List<Account> findByUser(User user);
    
    /**
     * Récupère tous les comptes d'un utilisateur spécifique par son ID
     * @param userId l'ID de l'utilisateur
     * @return la liste des comptes de l'utilisateur
     */
    List<Account> findByUserId(Long userId);
    
    /**
     * Récupère un compte par son numéro de compte
     * @param accountNumber le numéro de compte
     * @return le compte correspondant, s'il existe
     */
    Optional<Account> findByAccountNumber(String accountNumber);
    
    /**
     * Récupère tous les comptes d'un utilisateur d'un type spécifique
     * @param user l'utilisateur
     * @param accountType le type de compte (COURANT, EPARGNE)
     * @return la liste des comptes de l'utilisateur du type spécifié
     */
    List<Account> findByUserAndAccountType(User user, AccountType accountType);
    
    /**
     * Récupère tous les comptes d'un utilisateur d'un type spécifique par l'ID de l'utilisateur
     * @param userId l'ID de l'utilisateur
     * @param accountType le type de compte (COURANT, EPARGNE)
     * @return la liste des comptes de l'utilisateur du type spécifié
     */
    List<Account> findByUserIdAndAccountType(Long userId, AccountType accountType);
    
    /**
     * Vérifie si un numéro de compte existe déjà
     * @param accountNumber le numéro de compte
     * @return true si le numéro de compte existe, false sinon
     */
    boolean existsByAccountNumber(String accountNumber);
}
