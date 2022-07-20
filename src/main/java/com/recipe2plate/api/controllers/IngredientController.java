package com.recipe2plate.api.controllers;

import com.recipe2plate.api.dto.request.ingredient.CreateIngredientRequest;
import com.recipe2plate.api.dto.request.ingredient.UpdateIngredientRequest;
import com.recipe2plate.api.dto.response.IngredientDto;
import com.recipe2plate.api.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;


    @GetMapping("/{recipeId}")
    public ResponseEntity<List<IngredientDto>> allIngredients(@PathVariable Long recipeId) {
        final List<IngredientDto> ingredients = ingredientService.allIngredients(recipeId);
        return ResponseEntity.ok().body(ingredients);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addIngredient/{recipeId}")
    public ResponseEntity<IngredientDto> addIngredient(@Valid @RequestBody CreateIngredientRequest createIngredientRequest,
                                                       @PathVariable Long recipeId) {
        final IngredientDto ingredientDto = ingredientService.addIngredient(createIngredientRequest, recipeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/updateIngredient/{ingredientId}")
    public ResponseEntity<IngredientDto> updateIngredient(@Valid
                                                          @RequestBody UpdateIngredientRequest updateIngredientRequest,
                                                          @PathVariable Long ingredientId) {
        final IngredientDto updatedIngredient = ingredientService.updateIngredient(updateIngredientRequest, ingredientId);
        return ResponseEntity.accepted().body(updatedIngredient);

    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/deleteIngredient/{ingredientId}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }
}
