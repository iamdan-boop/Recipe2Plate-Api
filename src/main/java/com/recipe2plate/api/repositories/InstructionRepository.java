package com.recipe2plate.api.repositories;

import com.recipe2plate.api.entities.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {


    @Query(value = "SELECT * FROM instructions WHERE recipe_id = :recipeId", nativeQuery = true)
    List<Instruction> findAllByRecipeId(Long recipeId);


    @Query(value = "SELECT * FROM instructions WHERE recipe_id = :recipeId", nativeQuery = true)
    List<Instruction> findByRecipeId(Long recipeId);
}
