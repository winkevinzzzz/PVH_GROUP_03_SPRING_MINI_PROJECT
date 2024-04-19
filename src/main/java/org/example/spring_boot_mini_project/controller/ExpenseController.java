package org.example.spring_boot_mini_project.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.example.spring_boot_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        try {
            Expense savedExpense = expenseService.createExpense(expenseRequest);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", "The Expense has been successfully added.");
            response.put("payload", savedExpense);
            response.put("status", "CREATED");
            response.put("time", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping()
    public ResponseEntity <?> getAllExpense(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =authentication.getName();
        User user = userService.findUserByEmail(email);
        ExpenseResponse expenseResponse = expenseService.getAllExpenses(user.getUserId());

    }

}
