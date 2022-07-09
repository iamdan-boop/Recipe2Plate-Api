package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.RecipeDto;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;


    public List<Recipe> findAllRecipes() {
        return this.recipeRepository.findAll();
    }


    public Recipe addRecipe(RecipeDto recipeDto) {
        final Recipe recipe = Recipe.builder()
                .recipeName(recipeDto.getRecipeName())
                .description(recipeDto.getDescription())
                .build();
        return recipeRepository.save(recipe);
    }


    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        final Recipe findRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("Recipe not found"));
        findRecipe.setRecipeName(recipeDto.getRecipeName());
        findRecipe.setDescription(recipeDto.getDescription());
        return recipeRepository.save(findRecipe);
    }


    public void deleteRecipe(Long recipeId) {
        final Recipe findToDeleteRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("Not Recipe Found"));
        recipeRepository.delete(findToDeleteRecipe);
    }
}
