package com.recipe2plate.api.mapper;


import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.response.IngredientDto;
import com.recipe2plate.api.entities.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    Set<Ingredient> toIngredientsEntity(List<CreateIngredientRequest> ingredientRequests);


    Ingredient toIngredientEntity(CreateIngredientRequest createIngredientRequest);
    IngredientDto toIngredientDto(Ingredient ingredient);
}
