package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserRelationException;
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
    UserDto findByEmail(String email) throws UserNotFoundException;

    /**
     * Find DBUser by username
     *
     * @param userName the username
     * @return the user
     */
    UserDto findByUserName(String userName) throws UserNotFoundException;


    /**
     * Add a user in the database
     *
     * @param userDto the user to add
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    void addUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException;


    /**
     * Update a user
     *
     * @param userEmail the user email
     * @param userDto   updated datas of user
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     * @throws UserNotFoundException               the user not found exception
     */
    void updateUser(String userEmail, UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException, UserNotFoundException;


    /**
     * Add a relation between two users
     *
     * @param userEmail   the user email
     * @param friendEmail the friend email
     * @throws UserNotFoundException the user not found exception
     * @throws UserRelationException the user relation exception
     */
    void addRelation(String userEmail, String friendEmail) throws UserNotFoundException, UserRelationException;


}
