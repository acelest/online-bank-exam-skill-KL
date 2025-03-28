package com.banque.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banque.exception.ResourceNotFoundException;
import com.banque.model.Account;
import com.banque.model.AccountType;
import com.banque.model.User;
import com.banque.repository.AccountRepository;
import com.banque.service.AccountService;
import com.banque.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final UserService userService;
    
    @Override
    public List<Account> findAllByUsername(String username) {
        log.info("Récupération des comptes de l'utilisateur: {}", username);
        User user = userService.recupererParUsername(username);
        return accountRepository.findByUser(user);
    }
    
    @Override
    public Account findById(Long id) {
        log.info("Récupération du compte avec l'ID: {}", id);
        return accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Compte non trouvé avec l'ID: {}", id);
                    return new ResourceNotFoundException("Compte non trouvé");
                });
    }
    
    @Override
    @Transactional
    public Account createAccount(User user, AccountType accountType) {
        log.info("Création d'un compte {} pour l'utilisateur: {}", accountType, user.getUsername());
        
        // Générer un numéro de compte unique
        String accountNumber;
        do {
            accountNumber = generateAccountNumber(accountType);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUser(user);
        
        Account savedAccount = accountRepository.save(account);
        log.info("Compte {} créé avec succès pour l'utilisateur: {}", accountType, user.getUsername());
        
        return savedAccount;
    }
    
    /**
     * Génère un numéro de compte basé sur le type et un UUID aléatoire
     * @param accountType le type de compte
     * @return le numéro de compte généré
     */
    private String generateAccountNumber(AccountType accountType) {
        String prefix = accountType == AccountType.COURANT ? "CC" : "CE";
        String randomPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
        return prefix + randomPart;
    }
}
