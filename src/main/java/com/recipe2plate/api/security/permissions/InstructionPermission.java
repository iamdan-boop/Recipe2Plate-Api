package com.recipe2plate.api.security.permissions;

import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class InstructionPermission implements OwnershipPermissionControl<Instruction> {

    private final RecipeRepository recipeRepository;

    public InstructionPermission(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean shouldAuthorizeDestructiveActions(Optional<Instruction> targetObject) {
        if (targetObject.isEmpty()) {
            throw new NoRecordFoundException("Instruction not found.");
        }

        final Recipe recipe = recipeRepository.findRecipeByInstructionsContaining(targetObject.get())
                .orElseThrow(() -> new NoRecordFoundException("Recipe not found."));

        return currentAuthenticatedUser().getEmail().equals(recipe.getPublisher().getEmail());
    }
}
