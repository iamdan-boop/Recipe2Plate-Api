package com.recipe2plate.api.config.mapper;

import com.recipe2plate.api.dto.request.CreateCategoryRequest;
import com.recipe2plate.api.dto.response.CategoryDto;
import com.recipe2plate.api.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategoryEntity(CreateCategoryRequest category);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoriesDto(List<Category> categories);
}
