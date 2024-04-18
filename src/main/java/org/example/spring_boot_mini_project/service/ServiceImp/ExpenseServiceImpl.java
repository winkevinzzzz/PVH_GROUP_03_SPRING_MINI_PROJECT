package org.example.spring_boot_mini_project.service.ServiceImp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.repository.ExpenseRepository;
import org.example.spring_boot_mini_project.service.ExpenseService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Override
    public Expense createExpense(@Valid ExpenseRequest expenseRequest) {
        // Create a new Expense object
        Expense expense = new Expense();

        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setDate(expenseRequest.getDate());
        expense.setCategory_id(expenseRequest.getCategory_id());
        expenseRepository.createExpense(expense);
        return expense;
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.getExpenseById(id);
    }

    @Override
    public Expense updateExpense(Long id, ExpenseRequest expenseRequest) {
        return null;
    }


    @Override
    public Expense updateExpense(Expense expense) {
        expenseRepository.updateExpense(expense);
        return expense;
    }


    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteExpense(id);
    }

    @Override
    public List<Expense> getAllExpense(int offset, int limit, String sortBy, boolean ascending) {
        // Validate sort by field
        if (!Arrays.asList("date", "category_id").contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort by field. Must be 'date' or 'category_id'.");
        }

        // Get the expenses
        List<Expense> expenses = expenseRepository.getAllExpense(offset, limit, sortBy, ascending ? "asc" : "desc");

        return expenses;
    }




    @Override
    public List<Expense> getAllExpense() {
        return null;
    }

    @Override
    public Expense createExpense(Expense expense) {
        return null;
    }
}
