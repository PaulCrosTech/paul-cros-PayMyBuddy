package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;


/**
 * UserWithSameUserNameExistsException Class
 */
@Slf4j
public class UserWithSameUserNameExistsException extends RuntimeException {
    public UserWithSameUserNameExistsException(String userName) {
        super("A user with username '" + userName + "' already exists.");
        log.error("====> <exception> UserWithSameUserNameExistsException : {} <====", userName);
    }
}
