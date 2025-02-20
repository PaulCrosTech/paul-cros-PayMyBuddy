package com.openclassrooms.PayMyBuddy.model.dto;


import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPassword;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPasswordMatches;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@ValidPasswordMatches
@Data
public class DBUserRegisterDto {

    @ValidUsername
    private String userName;

    @NotEmpty(message = "L''email est obligatoire.")
    @Email(message = "L''email n''est pas valide.")
    private String email;

    @ValidPassword
    private String password;

    private String confirmPassword;

}
