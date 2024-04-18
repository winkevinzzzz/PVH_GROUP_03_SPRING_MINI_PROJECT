package org.example.spring_boot_mini_project.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

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

}
