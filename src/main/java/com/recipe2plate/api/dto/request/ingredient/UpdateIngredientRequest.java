package com.recipe2plate.api.dto.request.ingredient;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class UpdateIngredientRequest {

    @NotNull
    @NotEmpty
    private String ingredientName;
}
