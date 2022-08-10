package com.recipe2plate.api.mapper;

import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherAndCategory;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.services.FileSystemService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        AppUserMapper.class,
        IngredientMapper.class,
        InstructionMapper.class,
        FileSystemService.class,
        InstructionMapper.class,
})
public interface RecipeMapper {

    RecipeDto toSingleRecipeDto(Recipe recipe);

    @Mapping(target = "previewVideoUrl", source = "previewVideo", qualifiedByName = "mediaToUrlMp4")
    @Mapping(target = "previewImageUrl", source = "previewImage", qualifiedByName = "mediaToUrl")
    @Mapping(target = "categories", ignore = true)
    Recipe toRecipeEntity(CreateRecipeRequest createRecipeRequest);

    @Mapping(target = "publisherDto", source = "publisher")
    @Mapping(target = "categoriesDto", source = "categories")
    RecipeWithPublisherAndCategory toRecipeWithPublisherAndCategoryDto(Recipe recipe);

    @Mapping(target = "publisherDto", source = "publisher")
    @Mapping(target = "instructionsDto", source = "instructions")
    @Mapping(target = "categoriesDto", source = "categories")
    RecipeWithPublisherCategoryAndInstructions toRecipeWithPublisherCategoryAndInstruction(Recipe recipe);
}
