package com.openclassrooms.PayMyBuddy.service.impl;

import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import org.springframework.stereotype.Service;

/**
 * The class DBUser service.
 */
@Service
public class DBUserService implements IDBUserService {


    private final DBUserRepository dbUserRepository;

    /**
     * Constructor
     * @param dbUserRepository the db user repository
     */
    public DBUserService(DBUserRepository dbUserRepository) {
        this.dbUserRepository = dbUserRepository;
    }

    /**
     * Find DBUser by email
     *
     * @param email the email
     * @return the user
     */
    @Override
    public DBUser findByEmail(String email) {
        return dbUserRepository.findByEmail(email);
    }

    /**
     * Find DBUser by username
     * @param userName the username
     * @return the user
     */
    @Override
    public DBUser findByUserName(String userName) {
        return dbUserRepository.findByUserName(userName);
    }
}
