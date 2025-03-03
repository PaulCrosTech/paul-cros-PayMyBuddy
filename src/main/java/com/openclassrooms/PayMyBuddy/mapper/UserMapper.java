package com.openclassrooms.PayMyBuddy.mapper;

import com.openclassrooms.PayMyBuddy.entity.DBUser;
import com.openclassrooms.PayMyBuddy.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * User Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * UserDto To DBUser
     *
     * @param userDto the userDto
     * @return the db user
     */
    @Mapping(target = "password", ignore = true)
    DBUser toDBUser(UserDto userDto);

    /**
     * DBUser to UserDto
     *
     * @param dbUser the db user
     * @return the user dto
     */
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(DBUser dbUser);
}
