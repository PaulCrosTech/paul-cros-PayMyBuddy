package com.openclassrooms.PayMyBuddy.service.impl;

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
     * @param email the email
     * @return the user
     */
    @Override
    public UserDto findByEmail(String email) {
        DBUser dbUser = dbUserRepository.findByEmail(email);
        return userMapper.toUserDto(dbUser);
    }


    /**
     * Find DBUser by username
     *
     * @param userName the username
     * @return the user
     */
    @Override
    public UserDto findByUserName(String userName) {
        DBUser dbUser = dbUserRepository.findByUserName(userName);
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

        UserDto userWithSameEmail = findByEmail(userDto.getEmail());
        if (userWithSameEmail != null) {
            log.error("====> User with email {} already exists <====", userDto.getEmail());
            throw new UserWithSameEmailExistsException(userDto.getEmail());
        }

        UserDto userWithSameUsername = findByUserName(userDto.getUserName());
        if (userWithSameUsername != null) {
            log.error("====> User with username {} already exists <====", userDto.getUserName());
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
    public void updateUser(UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException {

        Authentication authentication = securityService.getAuthentication();
        UserDto currentUser = findByEmail(authentication.getName());
        log.info("====> Update profil : Current user  is {} <====", currentUser);
        log.info("====> Update profil : New datas are {} <====", userDto);

        if (!currentUser.getEmail().equals(userDto.getEmail())) {
            UserDto userWithSameEmail = findByEmail(userDto.getEmail());
            if (userWithSameEmail != null) {
                log.error("====> User with email {} already exists <====", userDto.getEmail());
                throw new UserWithSameEmailExistsException(userDto.getEmail());
            }
        }

        if (!currentUser.getUserName().equals(userDto.getUserName())) {
            UserDto userWithSameUsername = findByUserName(userDto.getUserName());
            if (userWithSameUsername != null) {
                log.error("====> User with username {} already exists <====", userDto.getUserName());
                throw new UserWithSameUserNameExistsException(userDto.getUserName());
            }
        }

        DBUser dBuser = userMapper.toDBUser(userDto);
        dBuser.setUserId(currentUser.getUserId());
        dBuser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        dbUserRepository.save(dBuser);

        securityService.reauthenticateUser(userDto.getEmail());
    }


}
