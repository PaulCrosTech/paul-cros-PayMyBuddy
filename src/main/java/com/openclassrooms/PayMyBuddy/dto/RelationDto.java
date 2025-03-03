package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidEmail;
import lombok.Data;

/**
 * DTO for relation
 */
@Data
public class RelationDto {
    
    @ValidEmail
    private String email;
}
