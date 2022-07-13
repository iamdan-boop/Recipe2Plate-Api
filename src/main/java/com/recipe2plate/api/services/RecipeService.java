package com.recipe2plate.api.services;


import com.recipe2plate.api.config.mapper.RecipeMapper;
import com.recipe2plate.api.dto.request.CreateRecipeRequest;
import com.recipe2plate.api.dto.response.RecipeDto;
import com.recipe2plate.api.dto.response.types.RecipeWithPublisher;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    private final FileSystemService fileSystemService;

    public List<Recipe> findAllRecipes() {
        return this.recipeRepository.findAll();
    }

    @Transactional
    public Recipe addRecipe(CreateRecipeRequest createRecipeRequest) {
        final Set<Instruction> instructions = createRecipeRequest.getInstructions().stream()
                .map(createInstructionDto -> {
                    try {
                        final String fileName = fileSystemService.saveImage(createInstructionDto.getInstructionMedia());
                        return Instruction.builder()
                                .name(createInstructionDto.getName())
                                .instruction(createInstructionDto.getInstruction())
                                .imageUrl(fileName)
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        final Recipe recipe = recipeMapper.toRecipeEntity(createRecipeRequest);
        recipe.setPublisher((AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        recipe.setInstructions(instructions);
        return recipeRepository.save(recipe);
    }


    public RecipeWithPublisher findRecipe(Long recipeId) {
        final Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("No Recipe Found"));
        return recipeMapper.toRecipeWithPublisherDto(recipe);
    }

    public Recipe updateRecipe(Long recipeId, RecipeDto recipeDto) {
        final Recipe findRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("Recipe not found"));
        findRecipe.setRecipeName(recipeDto.getRecipeName());
        findRecipe.setDescription(recipeDto.getDescription());
        return recipeRepository.save(findRecipe);
    }


    public void deleteRecipe(Long recipeId) {
        final Recipe findToDeleteRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoRecordFoundException("Not Recipe Found"));
        recipeRepository.delete(findToDeleteRecipe);
    }
}
