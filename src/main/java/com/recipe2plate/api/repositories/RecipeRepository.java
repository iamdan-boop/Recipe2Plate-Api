package com.recipe2plate.api.repositories;

import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.entities.Ingredient;
import com.recipe2plate.api.entities.Instruction;
import com.recipe2plate.api.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {


    @Query(value = "SELECT recipe FROM Recipe recipe " +
            "JOIN FETCH recipe.instructions instructions " +
            "LEFT JOIN FETCH recipe.categories" +
            " WHERE recipe.id = :recipeId")
    Optional<Recipe> findRecipeWithCategoriesInstructionsAndIngredients(Long recipeId);

    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);

    List<Recipe> findByCategoriesContaining(Category category);

    Optional<Recipe> findRecipeByIngredientsContaining(Ingredient ingredient);

    Optional<Recipe> findRecipeByInstructionsContaining(Instruction instruction);
}
