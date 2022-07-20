package com.recipe2plate.api.controllers;


import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> findAllCategories() {
        final List<CategoryDto> categories = categoryService.findAllCategories();
        return ResponseEntity.ok().body(categories);
    }
}
