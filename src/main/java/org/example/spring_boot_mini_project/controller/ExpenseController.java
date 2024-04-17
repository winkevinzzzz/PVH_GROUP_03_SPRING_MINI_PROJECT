package org.example.spring_boot_mini_project.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.service.ExpenseService;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        try {
            Expense expense = expenseService.getExpenseById(id);
            if (expense != null) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("message", "The expense has been successfully found.");
                response.put("payload", expense);
                response.put("status", "OK");
                response.put("time", LocalDateTime.now());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
