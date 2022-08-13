package com.recipe2plate.api.dto.request.ingredient;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateIngredientRequest {
    @NotNull
    @NotEmpty
    private String ingredientName;
}
