package com.banque.service;

import java.util.List;

import com.banque.model.Account;
import com.banque.model.AccountType;
import com.banque.model.User;

public interface AccountService {
    
    /**
     * Récupère tous les comptes d'un utilisateur
     * @param username le nom d'utilisateur
     * @return la liste des comptes
     */
    List<Account> findAllByUsername(String username);
    
    /**
     * Récupère un compte par son ID
     * @param id l'ID du compte
     * @return le compte trouvé
     */
    Account findById(Long id);
    
    /**
     * Crée un nouveau compte pour un utilisateur
     * @param user l'utilisateur propriétaire du compte
     * @param accountType le type de compte
     * @return le compte créé
     */
    Account createAccount(User user, AccountType accountType);
}
