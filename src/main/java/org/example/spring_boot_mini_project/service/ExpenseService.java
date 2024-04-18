package org.example.spring_boot_mini_project.service;

import com.sun.jdi.request.EventRequest;
import jakarta.validation.Valid;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {
    Expense getExpenseById(Long id);
    Expense updateExpense(Long id, ExpenseRequest expenseRequest);

    Expense updateExpense(Expense expense);


    void deleteExpense(Long id);

    List<Expense> getAllExpense(int offset, int limit, String sortBy, boolean ascending);
    List<Expense> getAllExpense();

    Expense createExpense(Expense expense);

    Expense createExpense(ExpenseRequest expenseRequest);
}
