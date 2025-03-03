package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidDescription;
import jakarta.validation.constraints.Positive;
import lombok.Data;


/**
 * DTO for the transfer page.
 */
@Data
public class TransferDto {

    @Positive(message = "Veuillez sélectionner une relation.")
    private int userId;

    @ValidDescription
    private String description;

    @Positive(message = "Veuillez saisir un montant positif.")
    private double amount;

}