package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidEmail;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPassword;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidUsername;
import lombok.Data;

/**
 * DTO for user
 */
@Data
public class UserDto {

    private int userId;

    @ValidUsername
    private String userName;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
