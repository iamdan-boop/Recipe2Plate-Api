package com.recipe2plate.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePostRequest {

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private Long referencedRecipe;
}
