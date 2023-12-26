package com.ackerman_levi.blogAPIs.services.impl;

import com.ackerman_levi.blogAPIs.entities.Category;
import com.ackerman_levi.blogAPIs.exceptions.ResourceNotFoundException;
import com.ackerman_levi.blogAPIs.payloads.CategoryDto;
import com.ackerman_levi.blogAPIs.repositories.CategoryRepo;
import com.ackerman_levi.blogAPIs.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = this.categoryRepo.save(category);

        return this.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategory(int id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream().map(this::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(int id) {
        Category category = this.categoryRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));
        this.categoryRepo.delete(category);
    }

    public CategoryDto categoryToCategoryDto (Category category){
        return this.modelMapper.map(category, CategoryDto.class);
    }

    public Category categoryDtoToCategory (CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto, Category.class);
    }
}
