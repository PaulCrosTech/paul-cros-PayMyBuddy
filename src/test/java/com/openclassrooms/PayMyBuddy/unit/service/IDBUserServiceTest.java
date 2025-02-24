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
     * - Given valid UserRegisterDto
     * - When addUser
     * - Then return created user
     */
    @Test
    public void givenValidDBUserRegisterDto_whenAddUser_thenReturnCreatedUser() {

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
}
