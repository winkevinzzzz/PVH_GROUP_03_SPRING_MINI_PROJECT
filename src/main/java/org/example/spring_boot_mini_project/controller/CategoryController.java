package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.CategoryRequest;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.service.CategoryService;
import org.example.spring_boot_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryResponse category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully created category ")
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .payload(category)
                        .build()

        );

    }

@GetMapping
public ResponseEntity<?> getAllCategories() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();
    User user = userService.findByEmail(email);
    List<CategoryResponse>  categoryResponses = categoryService.getAllCategory(user.getUserId());
    return ResponseEntity.ok(
            ApiResponse.builder()
                    .message("successfully get category ")
                    .status(HttpStatus.OK)
                    .code(200)
                    .payload(categoryResponses)
                    .build()

    );
}
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        CategoryResponse category = categoryService.getCategoryById(id,user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully created category ")
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .payload(category)
                        .build()

        );

    }


}
