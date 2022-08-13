package com.recipe2plate.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
