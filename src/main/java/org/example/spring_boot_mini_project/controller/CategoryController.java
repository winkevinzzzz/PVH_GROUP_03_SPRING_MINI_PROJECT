package org.example.spring_boot_mini_project.controller;

import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.CategoryRequest;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;
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
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.createCategory(categoryRequest);
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

//    @GetMapping
//    public ResponseEntity<?> getAllCategories() {
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String email = auth.getName();
//            User user = userService.findByEmail(email);
//
//            if (user == null) {
//                // Handle case where user is not found by email
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(ApiResponse.builder()
//                                .message("User not found for email: " + email)
//                                .status(HttpStatus.UNAUTHORIZED)
//                                .build());
//            }
//
//            List<CategoryResponse> categoryResponses = categoryService.getAllCategory(user.getUserId());
//            return ResponseEntity.ok(
//                    ApiResponse.builder()
//                            .message("successfully get category ")
//                            .status(HttpStatus.OK)
//                            .code(200)
//                            .payload(categoryResponses)
//                            .build()
//            );
//        } catch (Exception e) {
//            // Log the exception and return an error response
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.builder()
//                            .message("Internal server error")
//                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .build());
//        }
//    }




    @DeleteMapping("/id")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        return null;
    }


}
