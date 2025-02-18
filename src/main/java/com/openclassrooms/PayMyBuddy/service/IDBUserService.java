package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;

/**
 * The interface DBUser service.
 */
public interface IDBUserService {

    /**
     * Find DBUser by email
     *
     * @param email the email
     * @return the user
     */
    DBUser findByEmail(String email);

    /**
     * Find DBUser by username
     *
     * @param userName the username
     * @return the user
     */
    DBUser findByUserName(String userName);


    /**
     * Add a user to the database
     *
     * @param user the user to add
     * @return the user added
     * @throws UserWithSameEmailExistsException    if a user with the same email already exists
     * @throws UserWithSameUserNameExistsException if a user with the same username already exists
     */
    DBUser addUser(DBUserRegisterDto user) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException;
}
