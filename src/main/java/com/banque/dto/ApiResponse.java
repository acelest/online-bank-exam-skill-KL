package com.banque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour standardiser les réponses de l'API
 * @param <T> Type de données retourné dans la réponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * Statut de la réponse: SUCCES ou ERREUR
     */
    private String statut;
    
    /**
     * Message descriptif du résultat de l'opération
     */
    private String message;
    
    /**
     * Données retournées par l'opération, peut être null en cas d'erreur
     */
    private T data;
}
