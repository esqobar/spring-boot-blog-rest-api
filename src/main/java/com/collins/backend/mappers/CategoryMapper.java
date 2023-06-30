package com.collins.backend.mappers;

import com.collins.backend.dtos.CategoryDto;
import com.collins.backend.dtos.CommentDto;
import com.collins.backend.entities.Category;
import com.collins.backend.entities.Comment;

public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    //convert Dto into JPA Entity
    public static Category mapToCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
