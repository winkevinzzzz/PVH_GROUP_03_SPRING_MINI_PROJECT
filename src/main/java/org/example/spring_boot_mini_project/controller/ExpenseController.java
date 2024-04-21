package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.example.spring_boot_mini_project.service.ExpenseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @Operation(summary = "Add New Expense")
    public ResponseEntity<ApiResponse<ExpenseResponse>> createExpense(@Valid @RequestBody ExpenseRequest expenseRequest) {
        Expense expense = expenseService.createExpense(expenseRequest);
        ExpenseResponse expenseResponse = mapToExpenseResponse(expense);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ExpenseResponse>builder()
                        .message("The Expense has been successfully added.")
                        .status(HttpStatus.CREATED)
                        .payload(expenseResponse)
                        .time(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> getExpenseById(@PathVariable UUID id) {
        ExpenseResponse expense = expenseService.getExpenseById(id);
        if (expense != null) {
            ExpenseResponse expenseResponse = mapToExpenseResponse(expense);
            return ResponseEntity.ok(ApiResponse.<ExpenseResponse>builder()
                    .message("The expense has been successfully found.")
                    .status(HttpStatus.OK)
                    .payload(expenseResponse)
                    .time(LocalDateTime.now())
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get All Expenses")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        List<ExpenseResponse> expenseResponses = expenses.stream()
                .map(this::mapToExpenseResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.<List<ExpenseResponse>>builder()
                .message("All expenses have been successfully fetched.")
                .status(HttpStatus.OK)
                .payload(expenseResponses)
                .time(LocalDateTime.now())
                .build());
    }


    private ExpenseResponse mapToExpenseResponse(ExpenseResponse expense) {
        return ExpenseResponse.builder()
                .expenseId(expense.getExpenseId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .categoryId(expense.getCategoryId())
                .build();
    }
}
