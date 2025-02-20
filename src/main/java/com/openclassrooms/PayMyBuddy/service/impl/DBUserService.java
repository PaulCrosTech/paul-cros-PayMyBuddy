package com.openclassrooms.PayMyBuddy.service.impl;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.mapper.DBUserMapper;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The class DBUser service.
 */
@Slf4j
@Service
public class DBUserService implements IDBUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DBUserRepository dbUserRepository;
    private final DBUserMapper dbUserMapper;

    /**
     * Constructor
     *
     * @param dbUserRepository the db user repository
     */
    public DBUserService(DBUserRepository dbUserRepository,
                         DBUserMapper dbUserMapper,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.dbUserRepository = dbUserRepository;
        this.dbUserMapper = dbUserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
     *
     * @param userName the username
     * @return the user
     */
    @Override
    public DBUser findByUserName(String userName) {
        return dbUserRepository.findByUserName(userName);
    }

    /**
     * Add a user to the database
     *
     * @param userRegisterDto the user to add
     * @return the user added
     */
    @Override
    public DBUser addUser(DBUserRegisterDto userRegisterDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException {

        DBUser userWithSameEmail = findByEmail(userRegisterDto.getEmail());
        if (userWithSameEmail != null) {
            throw new UserWithSameEmailExistsException(userRegisterDto.getEmail());
        }

        DBUser userWithSameUsername = findByUserName(userRegisterDto.getUserName());
        if (userWithSameUsername != null) {
            throw new UserWithSameUserNameExistsException(userRegisterDto.getUserName());
        }

        DBUser user = dbUserMapper.toDBUser(userRegisterDto);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));

        return dbUserRepository.save(user);
    }
}
