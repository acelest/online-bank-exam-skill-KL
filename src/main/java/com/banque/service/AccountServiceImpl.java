package com.banque.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banque.dto.CreateAccountRequest;
import com.banque.exception.ResourceNotFoundException;
import com.banque.model.Account;
// Update the import to use Account.AccountType instead of standalone AccountType
import com.banque.model.Account.AccountType;
import com.banque.model.User;
import com.banque.repository.AccountRepository;
import com.banque.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    /**
     * Récupère tous les comptes d'un utilisateur donné
     * 
     * @param username le nom d'utilisateur
     * @return la liste des comptes de l'utilisateur
     */
    @Override
    public List<Account> findAllByUsername(String username) {
        // Vérification de l'existence de l'utilisateur
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + username));
        
        // Retourner les comptes de l'utilisateur
        return accountRepository.findByUser(user);
    }

    /**
     * Crée un nouveau compte bancaire pour un utilisateur
     * 
     * @param request les détails du compte à créer
     * @param username le nom d'utilisateur du propriétaire
     * @return le compte créé
     */
    @Transactional
    public Account createAccount(CreateAccountRequest request, String username) {
        // Vérification de l'existence de l'utilisateur
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + username));

        // Conversion du type de compte en AccountType
        AccountType type;
        try {
            type = AccountType.valueOf(request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Type de compte invalide. Les types valides sont: COURANT, EPARGNE");
        }

        // Création du compte
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(type) // This will now work with Account.AccountType
                .balance(BigDecimal.valueOf(request.getSoldeInitial())) // conversion correcte de solde initial
                .createdAt(LocalDateTime.now())
                .user(owner)
                .build();

        // Sauvegarder le compte dans la base de données
        return accountRepository.save(account);
    }

    /**
     * Récupère un compte par son ID et vérifie que l'utilisateur en est bien le propriétaire
     * 
     * @param accountId l'ID du compte à récupérer
     * @param username le nom d'utilisateur à vérifier
     * @return le compte s'il appartient à l'utilisateur
     * @throws ResourceNotFoundException si le compte n'existe pas ou n'appartient pas à l'utilisateur
     */
    public Account getAccountWithOwnerCheck(Long accountId, String username) {
        // Récupérer le compte par ID
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé avec l'ID: " + accountId));
        
        // Vérifier si l'utilisateur est bien le propriétaire du compte
        if (!account.getUser().getUsername().equals(username)) {
            log.warn("Tentative d'accès non autorisé au compte {} par {}", accountId, username);
            throw new ResourceNotFoundException("Compte non trouvé avec l'ID: " + accountId);
        }
        
        return account;
    }

    /**
     * Génère un numéro de compte unique
     * 
     * @return le numéro de compte généré
     */
    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
    }

    /**
     * Récupère un compte par son identifiant
     * 
     * @param id l'identifiant du compte
     * @return le compte correspondant
     * @throws ResourceNotFoundException si le compte n'existe pas
     */
    @Override
    public Account findById(Long id) {
        // Récupérer le compte par ID
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
    }

    /**
     * Crée un nouveau compte pour un utilisateur
     * 
     * @param user l'utilisateur propriétaire du compte
     * @param accountType le type de compte à créer
     * @return le compte créé
     */
    @Override
    public Account createAccount(User user, AccountType accountType) {
        // Création du compte
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(accountType) // This will now work with Account.AccountType
                .balance(BigDecimal.ZERO) // solde initial mis à zéro
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        // Sauvegarder le compte dans la base de données
        return accountRepository.save(account);
    }
}
