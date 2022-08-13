package com.recipe2plate.api.security.permissions;

import com.recipe2plate.api.entities.Ingredient;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientPermission implements OwnershipPermissionControl<Ingredient> {

    private final RecipeRepository recipeRepository;

    public IngredientPermission(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean shouldAuthorizeDestructiveActions(Optional<Ingredient> targetObject) {
        if (targetObject.isEmpty()) {
            throw new NoRecordFoundException("Ingredient Not Found");
        }
        final Recipe recipe = recipeRepository.findRecipeByIngredientsContaining(targetObject.get())
                .orElseThrow(() -> new NoRecordFoundException("Recipe Not Found"));

        return currentAuthenticatedUser().getEmail().equals(recipe.getPublisher().getEmail());
    }
}
