package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.request.CreateCategoryRequest;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.entities.Recipe;
import com.recipe2plate.api.repositories.RecipeRepository;
import com.recipe2plate.api.services.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    private final RecipeRepository recipeRepository;

    public CategoryController(CategoryService categoryService,
                              RecipeRepository recipeRepository) {
        this.categoryService = categoryService;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> findAllCategories() {
        final List<CategoryDto> categories = categoryService.findAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CreateCategoryRequest categoryRequest) {
        categoryService.addNewCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{category}")
    public ResponseEntity<List<Recipe>> findCategory(@PathVariable Category category,
                                                     @PageableDefault Pageable pageable) {
        final List<Recipe> searchByCategory = recipeRepository.findByCategoriesContaining(category, pageable);
        return ResponseEntity.ok().body(searchByCategory);
    }
}
