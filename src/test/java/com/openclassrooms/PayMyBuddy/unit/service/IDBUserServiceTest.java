package com.openclassrooms.PayMyBuddy.unit.service;

import com.openclassrooms.PayMyBuddy.exceptions.UserNotFoundException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.mapper.UserMapper;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.security.service.SecurityService;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import com.openclassrooms.PayMyBuddy.service.impl.DBUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IDBUserServiceTest {


    @Mock
    private DBUserRepository dbUserRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private SecurityService securityService;
    @Mock
    private UserMapper userMapper;


    private IDBUserService userService;

    @BeforeEach
    public void setUpPerTest() {
        userService = new DBUserService(dbUserRepository,
                bCryptPasswordEncoder,
                securityService,
                userMapper);
    }

    /**
     * Testing method findByEmail
     * - Given existing email
     * - When findByEmail
     * - Then return user
     */
    @Test
    public void givenExistingEmail_whenFindByEmail_thenReturnUser() {

        // Given
        String email = "mail@mail.fr";

        DBUser dbUser = new DBUser();
        dbUser.setEmail(email);

        UserDto userDto = new UserDto();
        userDto.setEmail(email);

        when(dbUserRepository.findByEmail(email)).thenReturn(Optional.of(dbUser));
        when(userMapper.toUserDto(dbUser)).thenReturn(userDto);

        // When
        UserDto actualUser = userService.findByEmail(email);

        // Then
        assertEquals(email, actualUser.getEmail());
    }

    /**
     * Testing method findByEmail
     * - Given non-existing email
     * - When findByEmail
     * - Then throw UserNotFoundException
     */
    @Test
    public void givenNonExistingEmail_whenFindByEmail_thenThrowUserNotFoundException() {

        // Given
        String email = "nonExistingEmail@mail.fr";
        when(dbUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                UserNotFoundException.class,
                () -> userService.findByEmail(email)
        );

    }


    /**
     * Testing method findByUserName
     * - Given existing username
     * - When findByUserName
     * - Then return user
     */
    @Test
    public void givenExistingUserName_whenFindByEmail_thenReturnUser() {


        // Given
        String userName = "userName";
        DBUser dbUser = new DBUser();
        dbUser.setUserName(userName);
        UserDto userDto = new UserDto();
        userDto.setUserName(userName);

        when(dbUserRepository.findByUserName(userName)).thenReturn(Optional.of(dbUser));
        when(userMapper.toUserDto(dbUser)).thenReturn(userDto);

        // When
        UserDto actualUser = userService.findByUserName(userName);

        // Then
        assertEquals(userName, actualUser.getUserName());

    }

    /**
     * Testing method findByUserName
     * - Given non-existing username
     * - When findByUserName
     * - Then throw UserNotFoundException
     */
    @Test
    public void givenNonExistingUserName_whenFindByEmail_thenThrowUserNotFoundException() {

        // Given
        String userName = "nonExistingUserName";
        when(dbUserRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                UserNotFoundException.class,
                () -> userService.findByUserName(userName)
        );

    }

    /**
     * Testing method addUser
     * - Given valid UserDto
     * - When addUser
     * - Then return created user
     */
    @Test
    public void givenValidUserDto_whenAddUser_thenReturnCreatedUser() {

        // Given
        UserDto userDto = new UserDto();
        DBUser dbUser = new DBUser();

        when(dbUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(dbUserRepository.findByUserName(userDto.getUserName())).thenReturn(Optional.empty());
        when(userMapper.toDBUser(userDto)).thenReturn(dbUser);
        when(dbUserRepository.save(dbUser)).thenReturn(dbUser);


        // When
        userService.addUser(userDto);

        // Then
        verify(dbUserRepository, times(1)).findByEmail(userDto.getEmail());
        verify(dbUserRepository, times(1)).findByUserName(userDto.getUserName());
        verify(userMapper, times(1)).toDBUser(userDto);
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(dbUserRepository, times(1)).save(dbUser);

    }

    /**
     * Testing method addUser
     * - Given existing email
     * - When addUser
     * - Then throw UserWithSameEmailExistsException
     */
    @Test
    public void givenExistingEmail_whenAddUser_thenThrowUserWithSameEmailExistsException() {

        // Given
        String email = "mail@mail.fr";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        DBUser dbUser = new DBUser();
        dbUser.setEmail(email);

        when(dbUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(dbUser));
        when(userMapper.toUserDto(dbUser)).thenReturn(userDto);

        // When & Then
        assertThrows(
                UserWithSameEmailExistsException.class,
                () -> userService.addUser(userDto)
        );

    }

    /**
     * Testing method addUser
     * - Given existing username
     * - When addUser
     * - Then throw UserWithSameUserNameExistsException
     */
    @Test
    public void givenExistingUsername_whenAddUser_thenThrowUserWithSameUserNameExistsException() {

        // Given
        String userName = "userName";
        String email = "mail@mail.fr";

        UserDto userDto = new UserDto();
        userDto.setUserName(userName);
        userDto.setEmail(email);

        DBUser dbUser = new DBUser();
        dbUser.setUserName(userName);
        dbUser.setEmail(email);

        when(dbUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        when(dbUserRepository.findByUserName(userDto.getUserName())).thenReturn(Optional.of(dbUser));
        when(userMapper.toUserDto(dbUser)).thenReturn(userDto);

        // When & Then
        assertThrows(
                UserWithSameUserNameExistsException.class,
                () -> userService.addUser(userDto)
        );

    }

    /**
     * Testing method updateUser
     * - Given valid UserDto
     * - When updateUser
     * - Then user is updated
     */
    @Test
    public void givenValidUserDto_whenUpdateUser_thenUserIsUpdated() {

        // Given

        // Current user datas
        UserDto currentUser = new UserDto();
        currentUser.setUserId(1);
        currentUser.setUserName("alice");
        currentUser.setEmail("alice@mail.fr");
        currentUser.setPassword("ActualPasswordEncoded");

        // Updated datas (only password is updated)
        UserDto userDto = new UserDto();
        userDto.setUserName(currentUser.getUserName());
        userDto.setEmail(currentUser.getEmail());
        userDto.setPassword("NewPasswordClear");

        when(securityService.getAuthenticationEmail()).thenReturn(currentUser.getEmail());
        when(dbUserRepository.findByEmail(currentUser.getEmail())).thenReturn(Optional.of(new DBUser()));
        when(userMapper.toUserDto(any(DBUser.class))).thenReturn(currentUser);


        when(userMapper.toDBUser(userDto)).thenReturn(new DBUser());
        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("NewPasswordEncoded");
        when(dbUserRepository.save(any(DBUser.class))).thenReturn(new DBUser());


        // When
        userService.updateUser(userDto);


        // Then
        verify(securityService, times(1)).getAuthenticationEmail();
        verify(dbUserRepository, times(1)).findByEmail(currentUser.getEmail());
        verify(userMapper, times(1)).toUserDto(any(DBUser.class));

        verify(userMapper, times(1)).toDBUser(userDto);
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(dbUserRepository, times(1)).save(any(DBUser.class));
        verify(securityService, times(1)).reauthenticateUser(userDto.getEmail());


    }


    /**
     * Testing method updateUser
     * - Given updated email with email already used
     * - When updateUser
     * - Then throw UserWithSameEmailExistsException
     */
    @Test
    public void givenUpdateEmailWithEmailAlreadyUsed_whenUpdateUser_thenThrowUserWithSameEmailExistsException() {
        // Current user datas
        UserDto currentUser = new UserDto();
        currentUser.setEmail("alice@mail.fr");

        // Updated datas
        UserDto userDto = new UserDto();
        userDto.setEmail("alreadyUsedEmail@mail.fr");

        DBUser dbUserWithSameEmail = new DBUser();
        dbUserWithSameEmail.setEmail(userDto.getEmail());

        when(securityService.getAuthenticationEmail()).thenReturn("alice@mail.fr");
        when(dbUserRepository.findByEmail("alice@mail.fr")).thenReturn(Optional.of(new DBUser()));
        when(userMapper.toUserDto(any(DBUser.class))).thenReturn(currentUser);

        when(dbUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(dbUserWithSameEmail));
        when(userMapper.toUserDto(dbUserWithSameEmail)).thenReturn(new UserDto());

        // When & Then
        assertThrows(UserWithSameEmailExistsException.class, () -> userService.updateUser(userDto));
        verify(dbUserRepository, never()).save(any(DBUser.class));

    }

    /**
     * Testing method updateUser
     * - Given updated username with username already used
     * - When updateUser
     * - Then throw UserWithSameUserNameExistsException
     */
    @Test
    public void givenUpdateUserNameWithUserNameAlreadyUsed_whenUpdateUser_thenThrowUserWithSameUserNameExistsException() {
        // Current user datas
        UserDto currentUser = new UserDto();
        currentUser.setEmail("alice@mail.fr");
        currentUser.setUserName("alice");

        // Updated datas
        UserDto userDto = new UserDto();
        userDto.setEmail("alice@mail.fr");
        userDto.setUserName("alreadyUsedUserName");

        DBUser dbUserWithSameUserName = new DBUser();
        dbUserWithSameUserName.setEmail(userDto.getEmail());
        dbUserWithSameUserName.setUserName(userDto.getUserName());


        when(securityService.getAuthenticationEmail()).thenReturn("alice@mail.fr");
        when(dbUserRepository.findByEmail("alice@mail.fr")).thenReturn(Optional.of(new DBUser()));
        when(userMapper.toUserDto(any(DBUser.class))).thenReturn(currentUser);

        when(dbUserRepository.findByUserName(userDto.getUserName())).thenReturn(Optional.of(dbUserWithSameUserName));
        when(userMapper.toUserDto(dbUserWithSameUserName)).thenReturn(new UserDto());

        // When & Then
        assertThrows(UserWithSameUserNameExistsException.class, () -> userService.updateUser(userDto));
        verify(dbUserRepository, never()).save(any(DBUser.class));

    }


}
