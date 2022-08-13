package com.recipe2plate.api.dto.request;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreatePostRequest {

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private Long referencedRecipe;
}
