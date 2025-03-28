package com.banque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la demande de création d'un compte
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    
    @NotBlank(message = "Le type de compte est obligatoire")
    private String type;
    
    @DecimalMin(value = "0.0", message = "Le solde initial ne peut pas être négatif")
    private double soldeInitial;
    
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;
}
