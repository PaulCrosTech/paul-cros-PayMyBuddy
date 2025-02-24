package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;

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
    UserDto findByEmail(String email);

    /**
     * Find DBUser by username
     *
     * @param userName the username
     * @return the user
     */
    UserDto findByUserName(String userName);


    /**
     * Add a user in the database
     *
     * @param userDto the user to add
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void addUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException;


    /**
     * Update user.
     *
     * @param userDto the user to update
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void updateUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException;
}
