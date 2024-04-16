package org.example.spring_boot_mini_project.service.ServiceImp;
//package com.example.springboot.service;

//import com.example.springboot.mapper.ExpenseMapper;
//import com.example.springboot.model.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.Request.ExpenseRequest;
import org.example.spring_boot_mini_project.repository.ExpenseMapper;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseMapper expenseMapper;

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

    @Override
    public ExpenseRequest createExpense(ExpenseRequest expenseRequest) {
        expenseMapper.createExpense(expenseRequest);
        return expenseRequest;
    }

//    @Override
//    public ExpenseDto getExpenseById(UUID id) {
//        return expenseMapper.getExpenseById(id);
//    }
//
//    @Override
//    public ExpenseDto updateExpense(UUID id, ExpenseDto expenseDto) {
//        expenseDto.setExpenseId(id);
//        expenseMapper.updateExpense(expenseDto);
//        return expenseDto;
//    }
//
//    @Override
//    public void deleteExpense(UUID id) {
//        expenseMapper.deleteExpense(id);
//    }
//
//    @Override
//    public List<ExpenseDto> getAllExpenses() {
//        return expenseMapper.getAllExpenses();
//    }
}
