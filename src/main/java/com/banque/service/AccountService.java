package com.banque.service;

import java.util.List;

import com.banque.model.Account;
import com.banque.model.Account.AccountType;
import com.banque.model.User;

/**
 * Service responsable de la gestion des comptes bancaires
 */
public interface AccountService {
    
    /**
     * Récupère tous les comptes d'un utilisateur donné
     * 
     * @param username le nom d'utilisateur
     * @return la liste des comptes de l'utilisateur
     */
    List<Account> findAllByUsername(String username);
    
    /**
     * Récupère un compte par son identifiant
     * 
     * @param id l'identifiant du compte
     * @return le compte correspondant
     * @throws com.banque.exception.ResourceNotFoundException si le compte n'existe pas
     */
    Account findById(Long id);
    
    /**
     * Crée un nouveau compte pour un utilisateur
     * 
     * @param user l'utilisateur propriétaire du compte
     * @param accountType le type de compte à créer
     * @return le compte créé
     */
    Account createAccount(User user, AccountType accountType);
}
