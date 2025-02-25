package com.openclassrooms.PayMyBuddy.service.impl;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.mapper.UserMapper;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.security.service.SecurityService;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class DBUser service.
 */
@Slf4j
@Service
public class DBUserService implements IDBUserService {


    private final DBUserRepository dbUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityService securityService;
    private final UserMapper userMapper;

    /**
     * Constructor
     *
     * @param dbUserRepository the db user repository
     */
    public DBUserService(DBUserRepository dbUserRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         SecurityService securityService,
                         UserMapper userMapper) {
        this.dbUserRepository = dbUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        this.securityService = securityService;
    }


    /**
     * Find DBUser by email
     *
     * @param email the email of the user
     * @return the user
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        DBUser dbUser = dbUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + email));
        return userMapper.toUserDto(dbUser);
    }


    /**
     * Find DBUser by username
     *
     * @param userName the username of the user
     * @return the user
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public UserDto findByUserName(String userName) throws UserNotFoundException {
        DBUser dbUser = dbUserRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec le username : " + userName));
        return userMapper.toUserDto(dbUser);
    }

    /**
     * Add a user in the database
     *
     * @param userDto the user to add in the database
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    @Transactional
    @Override
    public void addUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException {

        if (isUserExistWithSameEmail(userDto.getEmail())) {
            throw new UserWithSameEmailExistsException(userDto.getEmail());
        }

        if (isUserExistWithSameUserName(userDto.getUserName())) {
            throw new UserWithSameUserNameExistsException(userDto.getUserName());
        }

        DBUser dbUser = userMapper.toDBUser(userDto);
        dbUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        dbUserRepository.save(dbUser);
    }


    /**
     * Update user.
     *
     * @param userDto the user to update in the database
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     */
    @Transactional
    @Override
    public void updateUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException, UserNotFoundException {

        UserDto currentUser = findByEmail(securityService.getAuthenticationEmail());
        log.info("====> Update profil : Current user  is {} <====", currentUser);
        log.info("====> Update profil : New datas are {} <====", userDto);

        if (!currentUser.getEmail().equals(userDto.getEmail()) && isUserExistWithSameEmail(userDto.getEmail())) {
            throw new UserWithSameEmailExistsException(userDto.getEmail());
        }

        if (!currentUser.getUserName().equals(userDto.getUserName()) && isUserExistWithSameUserName(userDto.getUserName())) {
            throw new UserWithSameUserNameExistsException(userDto.getUserName());
        }

        DBUser dBuser = userMapper.toDBUser(userDto);
        dBuser.setUserId(currentUser.getUserId());
        dBuser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        dbUserRepository.save(dBuser);

        securityService.reauthenticateUser(userDto.getEmail());
    }

    /**
     * Check if a user exists with the same email
     *
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
    public boolean isUserExistWithSameEmail(String email) {
        try {
            findByEmail(email);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    /**
     * Check if a user exists with the same username
     *
     * @param userName the username to check
     * @return true if the user exists, false otherwise
     */
    public boolean isUserExistWithSameUserName(String userName) {
        try {
            findByUserName(userName);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

}
