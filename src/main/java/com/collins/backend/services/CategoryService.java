package com.collins.backend.services;

import com.collins.backend.dtos.CategoryDto;
import com.collins.backend.dtos.PostDto;
import com.collins.backend.entities.Category;
import com.collins.backend.entities.Post;
import com.collins.backend.exceptions.ResourceNotFoundException;
import com.collins.backend.exceptions.TitleAlreadyExistsException;
import com.collins.backend.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryDto addCategory(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
          return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id){
        Category existingCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        existingCategory.setId(id);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    public void deleteCategory(Long id){
        Category existingCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        categoryRepository.deleteById(id);
    }

}
