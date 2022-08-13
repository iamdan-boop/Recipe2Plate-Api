package com.recipe2plate.api.repositories;


import com.recipe2plate.api.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


    @Query("SELECT ingredient FROM Ingredient ingredient " +
            "LEFT JOIN FETCH ingredient.recipes recipe WHERE recipe.id = :recipeId")
    List<Ingredient> findAllIngredientsByRecipe(Long recipeId);
}

