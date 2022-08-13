package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.request.ingredient.UpdateIngredientRequest;
import com.recipe2plate.api.dto.response.IngredientDto;
import com.recipe2plate.api.entities.Ingredient;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.mapper.IngredientMapper;
import com.recipe2plate.api.repositories.IngredientRepository;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {


    private final IngredientRepository ingredientRepository;

    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientDto> allIngredients(Long recipeId) {
        return ingredientRepository.findAllIngredientsByRecipe(recipeId)
                .stream().map(ingredientMapper::toIngredientDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public IngredientDto addIngredient(CreateIngredientRequest createIngredientRequest,
                                       Recipe recipe) {
        final Ingredient ingredient = ingredientMapper.toIngredientEntity(createIngredientRequest);

        recipe.getIngredients().add(ingredient);
        recipeRepository.save(recipe);
        return ingredientMapper.toIngredientDto(ingredient);
    }


    public IngredientDto updateIngredient(UpdateIngredientRequest updateIngredientRequest,
                                          Ingredient ingredient) {
        ingredient.setIngredientName(updateIngredientRequest.getIngredientName());
        return ingredientMapper.toIngredientDto(ingredientRepository.save(ingredient));
    }



    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

}
