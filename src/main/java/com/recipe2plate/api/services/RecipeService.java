package com.recipe2plate.api.services;


import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.request.UpdateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.recipe.RecipeWithPublisherAndCategory;
import com.recipe2plate.api.dto.response.recipe.RecipeWithPublisherCategoryAndInstructions;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.mapper.RecipeMapper;
import com.recipe2plate.api.repositories.CategoryRepository;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;
    private final RecipeMapper recipeMapper;

    private final FileSystemService fileSystemService;

    public List<RecipeWithPublisherAndCategory> allRecipes() {
        return this.recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toRecipeWithPublisherAndCategoryDto)
                .collect(Collectors.toList());
    }

    public List<RecipeWithPublisherAndCategory> searchRecipe(String query) {
        if (query == null || query.isEmpty() || query.isBlank()) {
            return Collections.emptyList();
        }
        return recipeRepository.findByRecipeNameContainingIgnoreCase(query)
                .stream()
                .map(recipeMapper::toRecipeWithPublisherAndCategoryDto)
                .collect(Collectors.toList());
    }


    public List<RecipeWithPublisherAndCategory> searchRecipeByCategory(Category category) {
        return recipeRepository.findByCategoriesContaining(category)
                .stream()
                .map(recipeMapper::toRecipeWithPublisherAndCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecipeWithPublisherCategoryAndInstructions addRecipe(CreateRecipeRequest createRecipeRequest) throws Exception {
        final List<Category> categories = categoryRepository
                .findAllById(createRecipeRequest.getCategories());
        final Recipe recipe = recipeMapper.toRecipeEntity(createRecipeRequest);

        recipe.setCategories(new HashSet<>(categories));
        recipe.setPublisher((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        recipe.setPreviewImageUrl(fileSystemService.saveImage(createRecipeRequest.getPreviewImage()));

        return recipeMapper.toRecipeWithPublisherCategoryAndInstruction(recipeRepository.save(recipe));
    }

    public RecipeWithPublisherCategoryAndInstructions findRecipe(Long recipeId) {
        final Recipe recipe = recipeRepository.findRecipeWithCategoriesInstructionsAndIngredients(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("No Recipe Found"));
        return recipeMapper.toRecipeWithPublisherCategoryAndInstruction(recipe);
    }

    public RecipeDto updateRecipe(Recipe updateRecipe,
                                  UpdateRecipeRequest updateRecipeRequest) throws Exception {
        if (updateRecipeRequest.getPreviewImage() != null) {
            updateRecipe.setPreviewImageUrl(fileSystemService.
                    saveImage(updateRecipeRequest.getPreviewImage())
            );
        }

        if (updateRecipeRequest.getPreviewVideo() != null) {
            updateRecipe.setPreviewVideoUrl(fileSystemService.
                    saveVideoFile(updateRecipeRequest.getPreviewVideo())
            );
        }

        updateRecipe.setRecipeName(updateRecipeRequest.getRecipeName());
        updateRecipe.setDescription(updateRecipeRequest.getDescription());
        return recipeMapper.toSingleRecipeDto(recipeRepository.save(updateRecipe));
    }


    public void deleteRecipe(Long recipe) {
        recipeRepository.findById(recipe)
                .ifPresentOrElse(recipeRepository::delete, () -> {
                    throw new NoRecordFoundException("Recipe not found.");
                });
    }
}
