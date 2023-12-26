package com.ackerman_levi.blogAPIs.controllers;

import com.ackerman_levi.blogAPIs.payloads.ApiResponse;
import com.ackerman_levi.blogAPIs.payloads.CategoryDto;
import com.ackerman_levi.blogAPIs.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory (@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryDto> updatedCategory (@Valid @RequestBody CategoryDto categoryDto, @PathVariable int id){
        CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<CategoryDto>(updatedCategoryDto, HttpStatus.OK);
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<CategoryDto> getCategory (@PathVariable int id){
        CategoryDto categoryDto = this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.FOUND);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList = this.categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(categoryDtoList, HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }
}
