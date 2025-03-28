package com.banque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la demande de création d'un compte bancaire
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    
    @NotBlank(message = "Le type de compte est obligatoire")
    private String type;
    
    @Min(value = 0, message = "Le solde initial doit être positif ou nul")
    private double soldeInitial;
}
