package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * UserNotFoundException Class
 */
@Slf4j
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message (String) : Message of the exception
     */
    public UserNotFoundException(String message) {
        super(message);
        log.error("====> <exception> UserNotFoundException : {} <====", message);
    }
}
