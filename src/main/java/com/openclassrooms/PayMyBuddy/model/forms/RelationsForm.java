package com.openclassrooms.PayMyBuddy.model.forms;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidEmail;
import lombok.Data;

@Data
public class RelationsForm {

    @ValidEmail
    private String email;
}
