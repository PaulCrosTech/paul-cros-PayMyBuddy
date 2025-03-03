package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidDescription;
import jakarta.validation.constraints.Positive;
import lombok.Data;


/**
 * DTO for transaction
 */
@Data
public class TransactionDto {

    @Positive(message = "Veuillez sélectionner une relation.")
    private int userId;

    @ValidDescription
    private String description;

    @Positive(message = "Veuillez saisir un montant positif.")
    private double amount;

}