package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * UserWithSameUserNameExistsException Class
 */
@Slf4j
public class UserWithSameUserNameExistsException extends RuntimeException {

    /**
     * Constructor
     *
     * @param userName (String) : User name
     */
    public UserWithSameUserNameExistsException(String userName) {
        super("L'utilisateur '" + userName + "' existe déjà.");
        log.error("====> <exception> UserWithSameUserNameExistsException : {} <====", userName);
    }
}
