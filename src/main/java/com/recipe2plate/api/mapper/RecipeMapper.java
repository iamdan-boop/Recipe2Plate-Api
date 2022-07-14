package com.recipe2plate.api.mapper;

import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherAndCategory;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class, IngredientMapper.class})
public interface RecipeMapper {

    RecipeDto toSingleRecipeDto(Recipe recipe);

    @Mapping(target = "instructions", ignore = true)
    Recipe toRecipeEntity(CreateRecipeRequest createRecipeRequest);

    @Mapping(target = "recipeDto", source = "recipe")
    @Mapping(target = "publisherDto", source = "publisher")
    @Mapping(target = "categoriesDto", source = "categories")
    RecipeWithPublisherAndCategory toRecipeWithPublisherDto(Recipe recipe);

    @Mapping(target = "recipeDto", source = "recipe")
    @Mapping(target = "publisherDto", source = "publisher")
    @Mapping(target = "instructionsDto", source = "instructions")
    @Mapping(target = "categoriesDto", source = "categories")
    RecipeWithPublisherCategoryAndInstructions toRecipeWithPublisherCategoryAndInstruction(Recipe recipe);
}
