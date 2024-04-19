package org.example.spring_boot_mini_project.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.example.spring_boot_mini_project.service.UserService;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expenses")
@SecurityRequirement(name = "bearerAuth")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity <?> addExpense(@RequestBody ExpenseRequest expenseRequest){
        ExpenseResponse expenseResponse = expenseService.addExpense(expenseRequest);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully deleted category ")
                        .status(HttpStatus.OK)
                        .code(201)
                        .payload(expenseResponse)
                        .build()

        );
    }
    @GetMapping()
    public ResponseEntity <?> getAllExpense(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        User user = userService.findUserByEmail(email);
        List<ExpenseResponse> expenseResponse = expenseService.getAllExpenses(user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully deleted category ")
                        .status(HttpStatus.OK)
                        .code(201)
                        .payload(expenseResponse)
                        .build()

        );
    }
    @GetMapping("/{id}")
    public ResponseEntity <?> getExpenseById(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        User user = userService.findUserByEmail(email);
        ExpenseResponse expense = expenseService.getExpenseById(id,user.getUserId());
        return  ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully get expense")
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .payload(expense)
                        .build()
        );

    }
    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteExpenseById(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        User user = userService.findUserByEmail(email);
        expenseService.deleteExpenseById(id,user.getUserId());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully deleted category ")
                        .status(HttpStatus.OK)
                        .code(201)
                        .payload(null)
                        .build()
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity <?> updateExpenseById(@PathVariable UUID id,@RequestBody ExpenseRequest expenseRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        ExpenseResponse expenseResponse = expenseService.updateExpenseById(id,user.getUserId(),expenseRequest);
        return  ResponseEntity.ok(
                ApiResponse.builder()
                        .message("successfully get expense")
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .payload(expenseResponse)
                        .build());
    }

}
