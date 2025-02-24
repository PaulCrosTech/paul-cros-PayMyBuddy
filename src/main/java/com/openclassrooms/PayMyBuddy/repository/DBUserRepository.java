package com.openclassrooms.PayMyBuddy.repository;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface DBUser repository.
 */
@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Integer> {

    /**
     * Find User by email
     *
     * @param email the email
     * @return the user
     */
    Optional<DBUser> findByEmail(String email) throws UserNotFoundException;


    /**
     * Find User by username
     *
     * @param userName the username
     * @return the user
     */
    Optional<DBUser> findByUserName(String userName) throws UserNotFoundException;
}
