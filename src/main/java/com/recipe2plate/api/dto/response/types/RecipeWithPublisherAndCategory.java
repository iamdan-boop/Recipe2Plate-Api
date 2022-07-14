package com.recipe2plate.api.dto.response.types;

import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.dto.response.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeWithPublisherAndCategory {
    private RecipeDto recipeDto;
    private List<CategoryDto> categoriesDto;
    private AppUserDto publisherDto;
}