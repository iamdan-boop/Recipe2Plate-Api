package com.recipe2plate.api.controllers;

import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.request.UpdateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.recipe.RecipeWithPublisherAndCategory;
import com.recipe2plate.api.dto.response.recipe.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> allRecipes(@PageableDefault Pageable page) {
        final List<RecipeWithPublisherAndCategory> recipes = recipeService.allRecipes(page);
        return ResponseEntity.ok().body(recipes);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/{recipe}")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> findRecipe(@PathVariable Long recipe) {
        final RecipeWithPublisherCategoryAndInstructions findRecipe = recipeService.findRecipe(recipe);
        return ResponseEntity.ok().body(findRecipe);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> searchRecipe(@RequestParam String query,
                                                                             @PageableDefault Pageable page) {
        final List<RecipeWithPublisherAndCategory> searchRecipes = recipeService.searchRecipe(query, page);
        return ResponseEntity.ok().body(searchRecipes);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RecipeWithPublisherAndCategory>> searchByCategory(@PathVariable Category category,
                                                                                 @PageableDefault Pageable page) {
        final List<RecipeWithPublisherAndCategory> recipesByCategory = recipeService.searchRecipeByCategory(category, page);
        return ResponseEntity.ok().body(recipesByCategory);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = "/addRecipe")
    public ResponseEntity<RecipeWithPublisherCategoryAndInstructions> addRecipe(@Valid
                                                                                @ModelAttribute CreateRecipeRequest createRecipeRequest) throws Exception {
        recipeService.addRecipe(createRecipeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@recipePermission.shouldAuthorizeDestructiveActions(#recipe)")
    @PutMapping("/updateRecipe/{recipe}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Optional<Recipe> recipe,
                                                  @Valid @ModelAttribute UpdateRecipeRequest updateRecipeRequest) throws Exception {
        if (recipe.isEmpty()) throw new NoRecordFoundException("Recipe not found.");

        final RecipeDto updatedRecipe = recipeService.updateRecipe(recipe.get(), updateRecipeRequest);
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
