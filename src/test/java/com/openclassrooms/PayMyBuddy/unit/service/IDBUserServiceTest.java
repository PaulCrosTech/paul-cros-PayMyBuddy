package com.openclassrooms.PayMyBuddy.unit.service;

import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameEmailExistsException;
import com.openclassrooms.PayMyBuddy.exceptions.UserWithSameUserNameExistsException;
import com.openclassrooms.PayMyBuddy.mapper.DBUserMapper;
import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;
import com.openclassrooms.PayMyBuddy.repository.DBUserRepository;
import com.openclassrooms.PayMyBuddy.service.IDBUserService;
import com.openclassrooms.PayMyBuddy.service.impl.DBUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IDBUserServiceTest {


    @Mock
    private DBUserRepository dbUserRepository;
    @Mock
    private DBUserMapper dbUserMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private IDBUserService userService;

    @BeforeEach
    public void setUpPerTest() {
        userService = new DBUserService(dbUserRepository, dbUserMapper, bCryptPasswordEncoder);
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
        DBUser user = new DBUser();
        user.setEmail(email);
        when(dbUserRepository.findByEmail(email)).thenReturn(user);

        // When
        DBUser actualUser = userService.findByEmail(email);

        // Then
        assertEquals(email, actualUser.getEmail());
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
        DBUser user = new DBUser();
        user.setUserName(userName);
        when(dbUserRepository.findByUserName(userName)).thenReturn(user);

        // When
        DBUser actualUser = userService.findByUserName(userName);

        // Then
        assertEquals(userName, actualUser.getUserName());
    }

    /**
     * Testing method addUser
     * - Given valid DBUserRegisterDto
     * - When addUser
     * - Then return created user
     */
    @Test
    public void givenValidDBUserRegisterDto_whenAddUser_thenReturnCreatedUser() {

        // Given
        DBUserRegisterDto userRegisterDto = new DBUserRegisterDto();

        when(dbUserRepository.findByEmail(userRegisterDto.getEmail())).thenReturn(null);
        when(dbUserRepository.findByUserName(userRegisterDto.getUserName())).thenReturn(null);
        when(dbUserMapper.toDBUser(userRegisterDto)).thenReturn(new DBUser());
        when(dbUserRepository.save(new DBUser())).thenReturn(new DBUser());


        // When
        DBUser actualUser = userService.addUser(userRegisterDto);

        // Then
        verify(dbUserRepository, times(1)).findByEmail(userRegisterDto.getEmail());
        verify(dbUserRepository, times(1)).findByUserName(userRegisterDto.getUserName());
        verify(dbUserMapper, times(1)).toDBUser(userRegisterDto);
        verify(bCryptPasswordEncoder, times(1)).encode(userRegisterDto.getPassword());
        verify(dbUserRepository, times(1)).save(new DBUser());
        assertNotNull(actualUser);

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
        DBUserRegisterDto userRegisterDto = new DBUserRegisterDto();
        userRegisterDto.setEmail("mail@mail.fr");

        when(dbUserRepository.findByEmail(userRegisterDto.getEmail())).thenReturn(new DBUser());

        // When & Then
        assertThrows(
                UserWithSameEmailExistsException.class,
                () -> userService.addUser(userRegisterDto)
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
        DBUserRegisterDto userRegisterDto = new DBUserRegisterDto();
        userRegisterDto.setUserName("userName");

        when(dbUserRepository.findByUserName(userRegisterDto.getUserName())).thenReturn(new DBUser());

        // When & Then
        assertThrows(
                UserWithSameUserNameExistsException.class,
                () -> userService.addUser(userRegisterDto)
        );
    }
}
