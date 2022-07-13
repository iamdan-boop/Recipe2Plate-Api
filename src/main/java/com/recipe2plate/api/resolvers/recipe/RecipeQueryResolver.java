package com.recipe2plate.api.resolvers.recipe;

import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.repositories.RecipeRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class RecipeQueryResolver implements GraphQLQueryResolver {

    private final RecipeRepository recipeRepository;

    public List<Recipe> allRecipes() {
        return recipeRepository.findAll();
    }
}
