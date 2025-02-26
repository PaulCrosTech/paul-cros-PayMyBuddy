package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * UserNotFoundException Class
 */
@Slf4j
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
        log.error("====> <exception> UserNotFoundException : {} <====", message);
    }
}
