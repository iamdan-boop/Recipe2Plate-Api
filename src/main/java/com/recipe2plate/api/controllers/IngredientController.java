package com.recipe2plate.api.controllers;

import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.request.ingredient.UpdateIngredientRequest;
import com.recipe2plate.api.dto.response.IngredientDto;
import com.recipe2plate.api.entities.Ingredient;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<List<IngredientDto>> allIngredients(@PathVariable Long recipeId) {
        final List<IngredientDto> ingredients = ingredientService.allIngredients(recipeId);
        return ResponseEntity.ok().body(ingredients);
    }


    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@recipePermission.shouldAuthorizeDestructiveActions(#recipe)")
    @PostMapping("/addIngredient/{recipe}")
    public ResponseEntity<IngredientDto> addIngredient(@Valid
                                                       @RequestBody CreateIngredientRequest createIngredientRequest,
                                                       @PathVariable Optional<Recipe> recipe) {
        if (recipe.isEmpty()) throw new NoRecordFoundException("Recipe not found.");
        ingredientService.addIngredient(createIngredientRequest, recipe.get());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@ingredientPermission.shouldAuthorizeDestructiveActions(#ingredient)")
    @PutMapping("/updateIngredient/{ingredient}")
    public ResponseEntity<IngredientDto> updateIngredient(@Valid
                                                          @RequestBody UpdateIngredientRequest updateIngredientRequest,
                                                          @PathVariable Optional<Ingredient> ingredient) {
        if (ingredient.isEmpty()) throw new NoRecordFoundException("Ingredient not found.");
        final IngredientDto updatedIngredient = ingredientService.updateIngredient(updateIngredientRequest, ingredient.get());
        return ResponseEntity.accepted().body(updatedIngredient);

    }


    @PreAuthorize("hasRole('ROLE_USER') AND " +
            "@ingredientPermission.shouldAuthorizeDestructiveActions(#ingredient)")
    @DeleteMapping("/deleteIngredient/{ingredient}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Optional<Ingredient> ingredient) {
        if (ingredient.isEmpty()) throw new NoRecordFoundException("Ingredient not found.");
        ingredientService.deleteIngredient(ingredient.get());
        return ResponseEntity.noContent().build();
    }
}
