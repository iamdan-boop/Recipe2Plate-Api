package com.recipe2plate.api.controllers;

import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherAndCategory;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> allRecipes() {
        final List<RecipeWithPublisherAndCategory> recipes = recipeService.allRecipes();
        return ResponseEntity.ok().body(recipes);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{recipe}")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> findRecipe(@PathVariable Long recipe) {
        final RecipeWithPublisherCategoryAndInstructions findRecipe = recipeService.findRecipe(recipe);
        return ResponseEntity.ok().body(findRecipe);
    }

    @GetMapping
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> searchRecipe(@RequestParam String query) {
        final List<RecipeWithPublisherAndCategory> searchRecipes = recipeService.searchRecipe(query);
        return ResponseEntity.ok().body(searchRecipes);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> searchByCategory(@PathVariable Category category) {
        final List<RecipeWithPublisherAndCategory> recipesByCategory = recipeService.searchRecipeByCategory(category);
        return ResponseEntity.ok().body(recipesByCategory);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/addRecipe")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> addRecipe(@Valid @ModelAttribute
                                                                                CreateRecipeRequest createRecipeRequest) throws Exception {
        recipeService.addRecipe(createRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@recipePermission.shouldAuthorizeDestructiveActions(#recipe)")
    @PutMapping("/updateRecipe/{recipe}")
    public ResponseEntity<RecipeDto> updateRecipe(@Valid @RequestBody RecipeDto recipeDto,
                                                  @PathVariable Optional<Recipe> recipe) {
        if (recipe.isEmpty()) throw new NoRecordFoundException("Recipe not found.");

        final RecipeDto updatedRecipe = recipeService.updateRecipe(recipe.get(), recipeDto);
        return ResponseEntity.accepted().body(updatedRecipe);
    }


    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@recipePermission.shouldAuthorizeDestructiveActions(#recipe)")
    @DeleteMapping("/deleteRecipe/{recipe}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long recipe) {
        recipeService.deleteRecipe(recipe);
        return ResponseEntity.noContent().build();
    }
}
