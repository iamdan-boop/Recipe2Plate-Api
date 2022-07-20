package com.recipe2plate.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeDto {
    private String recipeName;
    private String description;
    private String previewImageUrl;
    private String previewVideoUrl;
}
