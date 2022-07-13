package com.recipe2plate.api.config.mapper;

import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisher;
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
    RecipeWithPublisher toRecipeWithPublisherDto(Recipe recipe);
}
