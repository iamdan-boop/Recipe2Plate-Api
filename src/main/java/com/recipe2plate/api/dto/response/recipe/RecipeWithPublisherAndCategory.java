package com.recipe2plate.api.dto.response.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe2plate.api.dto.response.AppUserDto;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.dto.response.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeWithPublisherAndCategory {
    private String recipeName;

    private String description;

    private String previewImageUrl;

    private String previewVideoUrl;

    @JsonProperty("categories")
    private List<CategoryDto> categoriesDto;

    @JsonProperty("publisher")
    private AppUserDto publisherDto;
}