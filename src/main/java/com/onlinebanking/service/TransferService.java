package com.onlinebanking.service;

import com.onlinebanking.model.Account;
import com.onlinebanking.model.Transaction;
import com.onlinebanking.repository.AccountRepository;
import com.onlinebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void transferBetweenAccounts(String fromAccount, String toAccount, double amount) {
        Account sourceAccount = accountRepository.findByAccountType(fromAccount)
            .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));

        Account destinationAccount = accountRepository.findByAccountType(toAccount)
            .orElseThrow(() -> new RuntimeException("Compte destination non trouvé"));

        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant");
        }

        // Mettre à jour les soldes
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        // Créer les transactions
        Transaction sourceTransaction = new Transaction();
        sourceTransaction.setAccount(sourceAccount);
        sourceTransaction.setAmount(-amount);
        sourceTransaction.setType("TRANSFER");
        sourceTransaction.setDescription("Transfert vers " + toAccount);
        sourceTransaction.setDate(LocalDateTime.now());

        Transaction destinationTransaction = new Transaction();
        destinationTransaction.setAccount(destinationAccount);
        destinationTransaction.setAmount(amount);
        destinationTransaction.setType("TRANSFER");
        destinationTransaction.setDescription("Transfert depuis " + fromAccount);
        destinationTransaction.setDate(LocalDateTime.now());

        // Sauvegarder les modifications
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        transactionRepository.save(sourceTransaction);
        transactionRepository.save(destinationTransaction);
    }

    @Transactional
    public void transferToRecipient(String recipientId, String accountType, double amount) {
        Account sourceAccount = accountRepository.findByAccountType(accountType)
            .orElseThrow(() -> new RuntimeException("Compte source non trouvé"));

        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant");
        }

        // Mettre à jour le solde
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);

        // Créer la transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(sourceAccount);
        transaction.setAmount(-amount);
        transaction.setType("TRANSFER");
        transaction.setDescription("Transfert vers le bénéficiaire " + recipientId);
        transaction.setDate(LocalDateTime.now());

        // Sauvegarder les modifications
        accountRepository.save(sourceAccount);
        transactionRepository.save(transaction);
    }
} 