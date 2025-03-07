package com.openclassrooms.PayMyBuddy.unit.mapper;

import com.openclassrooms.PayMyBuddy.dto.UserDto;
import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.mapper.UserMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type User mapper test.
 */
@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeAll
    public static void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    /**
     * Testing method toDBUser
     * - Given a UserDto
     * - When toDBUser
     * - Then return a DBUser
     */
    @Test
    public void givenUserDto_whenToDBUser_thenReturnDBUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("email@mail.fr");
        userDto.setUserName("userName");
        userDto.setPassword("Password@1");

        // When
        DBUser dbUser = userMapper.toDBUser(userDto);

        // Then
        assertEquals(userDto.getEmail(), dbUser.getEmail());
        assertEquals(userDto.getUserName(), dbUser.getUserName());
        assertNull(dbUser.getPassword());
    }

    /**
     * Testing method toUserDto
     * - Given a DBUser
     * - When toUserDto
     * - Then return a UserDto
     */
    @Test
    public void givenDBUser_whenToUserDto_thenReturnUserDto() {
        // Given
        DBUser dbUser = new DBUser();
        dbUser.setUserId(1);
        dbUser.setUserName("userName");
        dbUser.setEmail("email@mail.fr");
        dbUser.setPassword("EncryptedPassword");
        dbUser.setSolde(100.0);

        // When
        UserDto userDto = userMapper.toUserDto(dbUser);

        // Then
        assertEquals(dbUser.getEmail(), userDto.getEmail());
        assertEquals(dbUser.getUserName(), userDto.getUserName());
        assertNull(userDto.getPassword());
    }
}
