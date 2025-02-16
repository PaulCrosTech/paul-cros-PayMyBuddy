package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.model.DBUser;

/**
 * The interface DBUser service.
 */
public interface IDBUserService {

    /**
     * Find DBUser by email
     * @param email the email
     * @return the user
     */
    DBUser findByEmail(String email);

    /**
     * Find DBUser by username
     * @param userName the username
     * @return the user
     */
    DBUser findByUserName(String userName);
}
