package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.RecipeDto;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/")
    public ResponseEntity<List<Recipe>> findAllRecipes() {
        final List<Recipe> recipes = recipeService.findAllRecipes();
        return ResponseEntity.ok().body(recipes);
    }


    @PostMapping("/addRecipe")
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        final Recipe addRecipe = recipeService.addRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addRecipe);
    }


    @PutMapping("/updateRecipe/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long recipeId, @Valid @RequestBody RecipeDto recipeDto) {
        final Recipe updatedRecipe = recipeService.updateRecipe(recipeId, recipeDto);
        return ResponseEntity.accepted().body(updatedRecipe);
    }


    @DeleteMapping("/deleteRecipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }
}
