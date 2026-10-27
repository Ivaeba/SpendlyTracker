package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody Category category) {
        validateCategory(category);
        if (categoryRepository.existsByNameIgnoreCase(
                category.getName().trim())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Category already exists");
        }
        category.setName(category.getName().trim());
        return categoryRepository.save(category);
    }
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Long id,
            @RequestBody Category updatedCategory) {
        validateCategory(updatedCategory);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));
        String newName = updatedCategory.getName().trim();
        boolean nameUsed = categoryRepository.existsByNameIgnoreCase(newName);
        if (nameUsed && !category.getName().equalsIgnoreCase(newName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Category already exists");
        }
        category.setName(newName);
        category.setDescription(updatedCategory.getDescription());
        return categoryRepository.save(category);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);
    }
    private void validateCategory(Category category) {
        if (category.getName() == null
                || category.getName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Category name is required");
        }
    }
}
