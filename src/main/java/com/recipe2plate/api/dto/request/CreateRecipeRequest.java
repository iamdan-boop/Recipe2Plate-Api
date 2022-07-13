package com.recipe2plate.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateRecipeRequest {

    private String recipeName;

    private String description;

//    private List<Long> categories;

    private List<CreateIngredientRequest> ingredients;

    private List<CreateInstructionRequest> instructions;
}
