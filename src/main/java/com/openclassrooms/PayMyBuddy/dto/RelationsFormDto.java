package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidEmail;
import lombok.Data;

@Data
public class RelationsFormDto {

    @ValidEmail
    private String email;
}
