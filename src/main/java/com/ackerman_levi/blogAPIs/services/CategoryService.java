package com.ackerman_levi.blogAPIs.services;

import com.ackerman_levi.blogAPIs.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int id);
    CategoryDto getCategory(int id);
    List<CategoryDto> getAllCategory();
    void deleteCategory(int id);
}
