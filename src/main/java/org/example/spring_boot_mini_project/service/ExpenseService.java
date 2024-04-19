package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponse getExpenseById(UUID id, UUID userId);
    Expense updateExpense(UUID id, Expense expense);
    List<ExpenseResponse> getAllExpenses(UUID userId);
    ExpenseResponse addExpense(ExpenseRequest expenseRequest);

    void deleteExpenseById(UUID id, UUID userId);

    ExpenseResponse updateExpenseById(UUID id, UUID userId, ExpenseRequest expenseRequest);
}
