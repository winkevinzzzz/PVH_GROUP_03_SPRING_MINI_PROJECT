package org.example.spring_boot_mini_project.service;

import jakarta.validation.Valid;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    Expense getExpenseById(UUID id);
    Expense updateExpense(UUID id, Expense expense);

    void deleteExpense(UUID id);
    List<ExpenseResponse> getAllExpenses(UUID userId);
    Expense createExpense(Expense expense);

    Expense createExpense(ExpenseRequest expenseRequest);
}
