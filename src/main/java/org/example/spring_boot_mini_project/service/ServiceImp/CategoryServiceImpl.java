package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.CategoryRequest;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.repository.CategoryRepository;
import org.example.spring_boot_mini_project.repository.UserRepository;
import org.example.spring_boot_mini_project.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUserId(user);

        Category categoryForResponse = categoryRepository.createCategory(category);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);

        CategoryResponse categoryResponse = modelMapper.map(categoryForResponse,CategoryResponse.class);
        categoryResponse.setUserResponse(userResponse);
        return categoryForResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategory(UUID userId) {
        List<Category> categoryList = categoryRepository.getAllCategory(userId);
        List<CategoryResponse> categoryResponseList= new ArrayList<>();
        for (Category category : categoryList){
            CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }



    @Override
    public Category getCategoryById(Integer id) {
        return null;
    }

    @Override
    public void updateCategory(Integer id, CategoryRequest categoryRequest) {

    }

    @Override
    public void deleteCategory(Integer id) {

    }
}
