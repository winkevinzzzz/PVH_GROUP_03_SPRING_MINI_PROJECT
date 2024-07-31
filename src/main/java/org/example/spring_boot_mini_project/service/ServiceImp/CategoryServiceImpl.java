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
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUser(user);
        System.out.println(user.toString());

        Category categoryForResponse = categoryRepository.createCategory(category);
        System.out.println(categoryForResponse.toString());
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);

        CategoryResponse categoryResponse = modelMapper.map(categoryForResponse,CategoryResponse.class);
        categoryResponse.setUserResponse(userResponse);
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategory(UUID userId) {
        List<Category> categoryList = categoryRepository.getAllCategory(userId);
        System.out.println(categoryList.toString());
        List<CategoryResponse> categoryResponseList= new ArrayList<>();
        for (Category category : categoryList){
            UserResponse userResponse = modelMapper.map(category.getUser(),UserResponse.class);
            CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
            categoryResponse.setUserResponse(userResponse);
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }



    @Override
    public CategoryResponse getCategoryById(UUID id,UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        Category category = categoryRepository.getCategoryById(id,userId);
        CategoryResponse categoryResponse = modelMapper.map(category,CategoryResponse.class);
        categoryResponse.setUserResponse(userResponse);
        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest,UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        Category category = categoryRepository.updateCategory(id,categoryRequest,userId);
        CategoryResponse categoryResponse = modelMapper.map(category,CategoryResponse.class);
        categoryResponse.setUserResponse(userResponse);
        return categoryResponse;
    }

    @Override
    public void deleteCategoryById(UUID id, UUID userId) {
        categoryRepository.deleteCategoryById(id,userId);
    }
}
