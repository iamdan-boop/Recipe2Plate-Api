package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/")
    public ResponseEntity<List<RecipeWithPublisherCategoryAndInstructions>> allRecipes() {
        final List<RecipeWithPublisherCategoryAndInstructions> recipes = recipeService.allRecipes();
        return ResponseEntity.ok().body(recipes);
    }


    @GetMapping(value = "/{recipeId}")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> findRecipe(@PathVariable Long recipeId) {
        final RecipeWithPublisherCategoryAndInstructions recipe = recipeService.findRecipe(recipeId);
        return ResponseEntity.ok().body(recipe);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addRecipe")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> addRecipe(@Valid
                                                                                @ModelAttribute CreateRecipeRequest createRecipeRequest) throws Exception {
        final RecipeWithPublisherCategoryAndInstructions recipe = recipeService.addRecipe(createRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/updateRecipe/{recipeId}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long recipeId,
                                                  @Valid @RequestBody RecipeDto recipeDto) {
        final RecipeDto updatedRecipe = recipeService.updateRecipe(recipeId, recipeDto);
        return ResponseEntity.accepted().body(updatedRecipe);
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deleteRecipe/{recipeId}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }
}
