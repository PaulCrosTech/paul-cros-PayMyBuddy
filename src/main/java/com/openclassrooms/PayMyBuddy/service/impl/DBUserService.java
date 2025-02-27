package com.openclassrooms.PayMyBuddy.service.impl;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserRelationException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.mapper.UserMapper;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The class DBUser service.
 */
@Slf4j
@Service
public class DBUserService implements IDBUserService {


    private final DBUserRepository dbUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    /**
     * Constructor
     *
     * @param dbUserRepository the db user repository
     */
    public DBUserService(DBUserRepository dbUserRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         UserMapper userMapper) {
        this.dbUserRepository = dbUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
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
     * Update a user
     *
     * @param userEmail the user email
     * @param userDto   updated datas of user
     * @throws UserWithSameEmailExistsException    the user with the same email exists exception
     * @throws UserWithSameUserNameExistsException the user with the same username exists exception
     * @throws UserNotFoundException               the user not found exception
     */
    @Transactional
    @Override
    public void updateUser(String userEmail, UserDto userDto) throws UserWithSameEmailExistsException, UserWithSameUserNameExistsException, UserNotFoundException {

        DBUser currentDbUser = dbUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));

        log.info("====> Update profil : Current user  is {} <====", currentDbUser.getUserName());
        log.info("====> Update profil : New datas are {} <====", userDto.getUserName());

        if (!currentDbUser.getEmail().equals(userDto.getEmail()) && isUserExistWithSameEmail(userDto.getEmail())) {
            throw new UserWithSameEmailExistsException(userDto.getEmail());
        }

        if (!currentDbUser.getUserName().equals(userDto.getUserName()) && isUserExistWithSameUserName(userDto.getUserName())) {
            throw new UserWithSameUserNameExistsException(userDto.getUserName());
        }

        currentDbUser.setUserName(userDto.getUserName());
        currentDbUser.setEmail(userDto.getEmail());
        currentDbUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        dbUserRepository.save(currentDbUser);
    }


    /**
     * Add a relation between two users
     *
     * @param userEmail   the user email to add the relation
     * @param friendEmail the friend email to add the relation
     * @throws UserNotFoundException the user or friend is not found exception
     * @throws UserRelationException the user relation exception (self relation or already connected)
     */
    @Transactional
    @Override
    public void addRelation(String userEmail, String friendEmail) throws UserNotFoundException, UserRelationException {

        log.info("====> Adding relation between user '{}' and friend '{}' <====", userEmail, friendEmail);

        if (userEmail.equals(friendEmail)) {
            log.error("====> Error : User and friend email are the same, can't add self relation <====");
            throw new UserRelationException("Vous ne pouvez pas vous ajouter en ami.");
        }

        DBUser dbUser = dbUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));
        log.debug("====> User found : {} <====", dbUser);

        DBUser dbFriend = dbUserRepository.findByEmail(friendEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + friendEmail));
        log.debug("====> Friend found : {} <====", dbFriend);


        List<DBUser> connections = dbUser.getConnections();
        connections.stream()
                .filter(c -> c.getUserId() == dbFriend.getUserId())
                .findFirst()
                .ifPresent(c -> {
                    log.error("====> Error : User and friend are already connected <====");
                    throw new UserRelationException("Vous êtes déjà amis.");
                });

        dbUser.getConnections().add(dbFriend);
    }

    /**
     * Get all connections of a user
     *
     * @param userEmail the user email
     * @return the list of connections
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public List<DBUser> getConnections(String userEmail) throws UserNotFoundException {

        DBUser dbUser = dbUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));

        return dbUser.getConnections();
    }

    /**
     * Check if a user exists with the same email
     *
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
    private boolean isUserExistWithSameEmail(String email) {
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
    private boolean isUserExistWithSameUserName(String userName) {
        try {
            findByUserName(userName);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

}
