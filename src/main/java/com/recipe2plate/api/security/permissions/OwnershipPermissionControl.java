package com.recipe2plate.api.security.permissions;

import com.recipe2plate.api.entities.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public interface OwnershipPermissionControl<T> {
    boolean shouldAuthorizeDestructiveActions(Optional<T> targetObject);

    default AppUser currentAuthenticatedUser() {
        return (AppUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
