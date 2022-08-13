package com.recipe2plate.api.dto.response.post;


import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.RecipeDto;
import lombok.Data;

@Data
public class PostDto {
    private Long id;

    private String description;

    private RecipeDto referencedRecipe;

    private AppUserDto postPublisher;
}
