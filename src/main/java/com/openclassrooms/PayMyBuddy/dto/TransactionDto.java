package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidDescription;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidUsername;
import jakarta.validation.constraints.Positive;
import lombok.Data;


/**
 * DTO for transaction
 */
@Data
public class TransactionDto {

    @ValidUsername(message = "Veuillez sélectionner une relation.")
    private String userName = "";

    @ValidDescription
    private String description;

    @Positive(message = "Veuillez saisir un montant positif.")
    private double amount;

}