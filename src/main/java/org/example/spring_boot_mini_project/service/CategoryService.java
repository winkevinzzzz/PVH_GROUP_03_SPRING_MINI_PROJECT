package org.example.spring_boot_mini_project.service;

import org.apache.ibatis.javassist.NotFoundException;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.dto.request.CategoryRequest;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategory(UUID userId, int pageNumber,int pageSize);
    CategoryResponse getCategoryById (UUID id,UUID userId) throws NotFoundException, FindNotFoundException;
    CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest,UUID userId) throws FindNotFoundException;
    void deleteCategoryById(UUID id, UUID userId);
}