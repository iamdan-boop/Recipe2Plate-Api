package com.recipe2plate.api.resolvers.recipe.resolvers;

import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.repositories.InstructionRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class RecipeInstructionQueryResolver implements GraphQLResolver<Recipe> {

    private final InstructionRepository instructionRepository;

    public List<Instruction> instructions(Recipe recipe) {
        return instructionRepository.findByRecipeId(recipe.getId());
    }
}
