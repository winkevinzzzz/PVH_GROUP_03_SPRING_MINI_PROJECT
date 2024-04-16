package org.example.spring_boot_mini_project.repository;
//package com.example.springboot.mapper;

//import com.example.springboot.model.dto.ExpenseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.example.spring_boot_mini_project.model.Request.ExpenseRequest;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ExpenseMapper {

    @Insert("INSERT INTO Expenses (amount, description, date, user_id, category_id) " +
            "VALUES (#{amount}, #{description}, #{date}, #{userId}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "expense_id")
    void createExpense(ExpenseRequest expenseRequest);

//    ExpenseRequest getExpenseById(UUID id);
//
//    void updateExpense(ExpenseDto expenseDto);
//
//    void deleteExpense(UUID id);
//
//    List<ExpenseDto> getAllExpenses();

}
