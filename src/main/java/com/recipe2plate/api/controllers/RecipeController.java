package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/")
    public ResponseEntity<List<Recipe>> findAllRecipes() {
        final List<Recipe> recipes = recipeService.allRecipes();
        return ResponseEntity.ok().body(recipes);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{recipeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recipe> findRecipe(@PathVariable Long recipeId) {
        final Recipe recipe = recipeService.findRecipe(recipeId);
        return ResponseEntity.ok().body(recipe);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addRecipe")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> addRecipe(@Valid
                                                                                @ModelAttribute CreateRecipeRequest createRecipeRequest) throws Exception {
        final RecipeWithPublisherCategoryAndInstructions recipe = recipeService.addRecipe(createRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }


    @PutMapping("/updateRecipe/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long recipeId,
                                               @Valid @RequestBody RecipeDto recipeDto) {
        final Recipe updatedRecipe = recipeService.updateRecipe(recipeId, recipeDto);
        return ResponseEntity.accepted().body(updatedRecipe);
    }


    @DeleteMapping("/deleteRecipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }
}
