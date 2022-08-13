package com.recipe2plate.api.dto.request;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdatePostRequest {
    @NotNull
    @NotEmpty
    private String description;

    @Nullable
    private Long referenceRecipe;
}
