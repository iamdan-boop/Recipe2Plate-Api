package com.recipe2plate.api.config.mapper;

import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

//    @Mapping(target = "id", source = "appUser.id")
//    @Mapping(target = "firstName", source = "appUser.firstName")
//    @Mapping(target = "lastName", source = "appUser.lastName")
//    @Mapping(target = "email", source = "appUser.email")
    AppUserDto toAppUserDto(AppUser appUser);
}
