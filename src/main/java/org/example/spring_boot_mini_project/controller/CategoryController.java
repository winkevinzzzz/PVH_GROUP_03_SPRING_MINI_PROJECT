package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.ibatis.javassist.NotFoundException;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    LocalDateTime currentTime = LocalDateTime.now();

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid  @RequestBody CategoryRequest categoryRequest){
        CategoryResponse category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully created category ")
                        .status(HttpStatus.CREATED)
                        .payload(category)
                        .time(currentTime)
                        .build()

        );

    }

@GetMapping
public ResponseEntity<?> getAllCategories(@RequestParam(required = false, defaultValue = "1") String page,
                                          @RequestParam(required = false, defaultValue = "5") String size) {
    int pageNumber = Integer.parseInt(page);
    int pageSize = Integer.parseInt(size);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();
    User user = userService.findUserByEmail(email);
    List<CategoryResponse>  categoryResponses = categoryService.getAllCategory(user.getUserId(),pageNumber,pageSize);

    return ResponseEntity.ok(
            ApiResponse.builder()
                    .message("All categories have been successfully founded")
                    .payload(categoryResponses)
                    .status(HttpStatus.OK)
                    .time(currentTime)
                    .build()

    );
}
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable UUID id) throws FindNotFoundException, NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findUserByEmail(email);
        CategoryResponse category = categoryService.getCategoryById(id,user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("The category has been successfully founded.")
                        .status(HttpStatus.OK)
                        .payload(category)
                        .time(currentTime)
                        .build()

        );

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable UUID id) throws FindNotFoundException, NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        System.out.println(user.toString());
        if(categoryService.getCategoryById(id,user.getUserId())==null)
        {
            throw new FindNotFoundException("Category with this id is not exist");
        }
       categoryService.deleteCategoryById(id,user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully deleted category ")
                        .status(HttpStatus.OK)
                        .payload(null)
                        .time(currentTime)
                        .build()

        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@Validated  @PathVariable UUID id,@Validated @RequestBody CategoryRequest categoryRequest) throws FindNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        CategoryResponse categoryResponse = categoryService.updateCategory(id,categoryRequest,user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully updated category ")
                        .status(HttpStatus.OK)
                        .payload(categoryResponse)
                        .time(currentTime)
                        .build());
    }


}
