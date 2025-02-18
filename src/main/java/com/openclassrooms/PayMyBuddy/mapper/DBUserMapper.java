package com.openclassrooms.PayMyBuddy.mapper;


import com.openclassrooms.PayMyBuddy.model.DBUser;
import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The interface DBUser mapper.
 */
@Mapper(componentModel = "spring")
public interface DBUserMapper {

    /**
     * DBUserRegisterDto To DBUser
     *
     * @param dbUserRegisterDto the db user register dto
     * @return the db user
     */
    @Mapping(target = "password", ignore = true)
    DBUser toDBUser(DBUserRegisterDto dbUserRegisterDto);

}
