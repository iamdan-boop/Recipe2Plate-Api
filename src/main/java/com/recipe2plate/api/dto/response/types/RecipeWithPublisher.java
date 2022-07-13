package com.recipe2plate.api.dto.response.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeWithPublisher {
    @JsonProperty("recipe")
    private RecipeDto recipeDto;
    @JsonProperty("publisher")
    private AppUserDto publisherDto;
}
