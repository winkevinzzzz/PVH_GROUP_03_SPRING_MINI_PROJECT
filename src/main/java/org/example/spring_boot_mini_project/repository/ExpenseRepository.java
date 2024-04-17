package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import org.example.spring_boot_mini_project.model.Expense;

@Mapper
@Repository
public interface ExpenseRepository {

    @Insert("INSERT INTO Expenses (amount, description, date, category_id) " +
            "VALUES (#{amount}, #{description}, #{date}, #{category_id})")
    @Options(useGeneratedKeys = true, keyProperty = "expense_id")
    void createExpense(Expense expense);

    @Select("SELECT * FROM Expenses WHERE expense_id = #{id}")
    Expense getExpenseById(Long id);
}
