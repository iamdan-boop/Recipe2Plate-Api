package com.recipe2plate.api.security.permissions;

import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class RecipePermission implements OwnershipPermissionControl<Recipe> {
    @Override
    public boolean shouldAuthorizeDestructiveActions(Optional<Recipe> targetObject) {
        if (targetObject.isEmpty()) {
            throw new NoRecordFoundException("Recipe not found.");
        }

        return targetObject.map((target) ->
                currentAuthenticatedUser().getEmail().equals(target.getPublisher().getEmail())
        ).get();
    }
}
