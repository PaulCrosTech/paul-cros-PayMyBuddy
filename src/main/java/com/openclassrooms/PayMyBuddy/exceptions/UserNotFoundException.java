package com.openclassrooms.PayMyBuddy.exceptions;

/**
 * UserNotFoundException Class
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
