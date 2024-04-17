package org.example.spring_boot_mini_project.service;

import jakarta.validation.Valid;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {
    Expense getExpenseById(Long id);
    Expense updateExpense(java.util.UUID id, Expense expense);

    void deleteExpense(java.util.UUID id);
    List<Expense> getAllExpenses();
    Expense createExpense(Expense expense);

    Expense createExpense(ExpenseRequest expenseRequest);
}
