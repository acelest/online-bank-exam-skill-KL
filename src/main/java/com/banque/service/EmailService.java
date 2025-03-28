package com.banque.service;

public interface EmailService {
    
    /**
     * Envoie un email simple
     * @param destinataire l'adresse email du destinataire
     * @param sujet le sujet de l'email
     * @param contenu le contenu de l'email
     */
    void envoyerEmail(String destinataire, String sujet, String contenu);
}
