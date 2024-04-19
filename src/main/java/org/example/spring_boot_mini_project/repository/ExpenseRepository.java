package org.example.spring_boot_mini_project.repository;


import org.apache.ibatis.annotations.*;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface ExpenseRepository {

    @Insert("INSERT INTO Expenses (amount, description, date, category_id) " +
            "VALUES (#{amount}, #{description}, #{expenseDate}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "expenseId")
    void createExpense(ExpenseRequest expense);

    @Select("""
        SELECT * FROM expenses
        WHERE user_id =#{UserID}
    """)
    @Results(id = "expenseMapping", value = {
            @Result(property = "expenseId",column = "expense_id"),
            @Result(property = "amount",column = "amount"),
            @Result(property = "description",column = "description"),
            @Result(property = "date",column = "date"),
            @Result(property = "user",column = "user_id",
            one = @One(select = "org.example.spring_boot_mini_project.repository.CategoryRepository.findCategoryById"))
    })


    List<Expense> getAllExpense();
}
