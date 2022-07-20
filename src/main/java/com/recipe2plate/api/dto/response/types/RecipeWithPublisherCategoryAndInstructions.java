package com.recipe2plate.api.dto.response.types;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.dto.response.InstructionDto;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RecipeWithPublisherCategoryAndInstructions {
    @JsonProperty("recipe")
    private RecipeDto recipeDto;
    @JsonProperty("instructions")
    private List<InstructionDto> instructionsDto;
    @JsonProperty("categories")
    private List<CategoryDto> categoriesDto;
    @JsonProperty("publisher")
    private AppUserDto publisherDto;
}
