package org.example.spring_boot_mini_project.service;


import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.Request.ExpenseRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface ExpenseService {
    Expense getExpenseById(UUID id);
    Expense updateExpense(UUID id, Expense expense);

    static Expense addExpense(@Valid ExpenseRequest expense) {
        return null;
    }

    void deleteExpense(UUID id);
    List<Expense> getAllExpenses();
    Expense createExpense(Expense expense);

    ExpenseRequest createExpense(ExpenseRequest expenseRequest);
}