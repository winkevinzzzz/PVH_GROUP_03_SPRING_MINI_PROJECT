package org.example.spring_boot_mini_project.service.ServiceImp;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.repository.ExpenseRepository;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.springframework.stereotype.Service;

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

        // Set values from ExpenseRequest to the Expense object
        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setExpense_date(expenseRequest.getExpenseDate());
        expense.setCategory_id(expenseRequest.getCategoryId());

        // Call the repository method to create the expense
        expenseRepository.createExpense(expenseRequest);

        // Return the created Expense object
        return expense;
    }

    @Override
    public Expense getExpenseById(UUID id) {
        return null;
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
