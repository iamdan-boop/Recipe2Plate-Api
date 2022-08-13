package com.recipe2plate.api.mapper;

import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDto toAppUserDto(AppUser appUser);
}
