package com.recipe2plate.api.dto.request.ingredient;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateIngredientRequest {

    @NotNull
    @NotEmpty
    private String ingredientName;
}
