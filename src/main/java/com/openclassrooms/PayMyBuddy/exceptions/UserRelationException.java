package com.openclassrooms.PayMyBuddy.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * UserRelationException Class
 */
@Slf4j
public class UserRelationException extends RuntimeException {
    public UserRelationException(String message) {
        super(message);
        log.error("====> <exception> UserRelationException : {} <====", message);
    }
}
