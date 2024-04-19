package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.dto.request.CategoryRequest;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategory(UUID userId);
    CategoryResponse getCategoryById (UUID id,UUID userId);
    void updateCategory(Integer id, CategoryRequest categoryRequest);
    void deleteCategory(Integer id);

}