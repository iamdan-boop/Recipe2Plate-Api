package com.recipe2plate.api.services;

import com.recipe2plate.api.dto.request.CreateCategoryRequest;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.entities.Category;
import com.recipe2plate.api.mapper.CategoryMapper;
import com.recipe2plate.api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryDto> findAllCategories() {
        final List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoriesDto(categories);
    }

    public CategoryDto addNewCategory(CreateCategoryRequest categoryRequest) {
        final Category category = categoryMapper.toCategoryEntity(categoryRequest);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }
}