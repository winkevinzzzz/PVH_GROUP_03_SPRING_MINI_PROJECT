package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    ExpenseResponse getExpenseById(UUID id);

    ExpenseResponse getExpenseById(UUID id, UUID userId);

    Expense updateExpense(Long id, ExpenseRequest expenseRequest);

    Expense updateExpense(Expense expense);

    void deleteExpense(UUID id);

    List<Expense> getAllExpenses(int offset, int limit, String sortBy, boolean ascending);


    List<Expense> getAllExpense(int offset, int limit, String sortBy, boolean ascending);

    List<Expense> getAllExpense();

    Expense createExpense(ExpenseRequest expense);

//    Expense createExpense(ExpenseRequest expenseRequest);

    ExpenseResponse addExpense(ExpenseRequest expenseRequest);
}
