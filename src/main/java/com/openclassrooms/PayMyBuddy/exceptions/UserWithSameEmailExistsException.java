package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * UserWithSameEmailExistsException Class
 */
@Slf4j
public class UserWithSameEmailExistsException extends RuntimeException {

    /**
     * Constructor
     *
     * @param email (String) : Email of the user
     */
    public UserWithSameEmailExistsException(String email) {
        super("Un utilisateur avec l'email '" + email + "' existe déjà.");
        log.error("====> <exception> UserWithSameEmailExistsException : {} <====", email);
    }
}
