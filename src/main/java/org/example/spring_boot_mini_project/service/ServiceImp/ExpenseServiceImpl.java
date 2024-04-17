package org.example.spring_boot_mini_project.service.ServiceImp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.repository.ExpenseRepository;
import org.example.spring_boot_mini_project.service.ExpenseService;

import java.util.List;

import java.util.UUID;

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
    public Expense updateExpense(UUID id, Expense expense) {
        return null;
    }

    @Override
    public void deleteExpense(UUID id) {

    }

    @Override
    public List<Expense> getAllExpenses() {
        return null;
    }

    @Override
    public Expense createExpense(Expense expense) {
        return null;
    }
}
