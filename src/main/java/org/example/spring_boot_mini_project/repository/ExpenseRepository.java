package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
//import org.example.spring_boot_mini_project.typehandler.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.springframework.stereotype.Repository;

import org.example.spring_boot_mini_project.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface ExpenseRepository {

    @Insert("INSERT INTO Expenses (amount, description, date, category_id) " +
            "VALUES (#{amount}, #{description}, #{date}, #{category_id})")
    @Options(useGeneratedKeys = true, keyProperty = "expense_id")
    void createExpense(Expense expense);

    @Select("SELECT * FROM expenses WHERE expense_id = #{expense_id}")
    @Results(id = "expenseResultMap", value = {
            @Result(property = "expenseId", column = "expense_id"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "description", column = "description"),
            @Result(property = "date", column = "date", javaType = LocalDateTime.class, typeHandler = LocalDateTimeTypeHandler.class),
            @Result(property = "categoryId", column = "category_id")
    })
    Expense getExpenseById(Long id);

    @Update("UPDATE expenses SET amount = #{amount}, description = #{description}, date = #{date}, category_id = #{category_id} WHERE expense_id = #{id}")
    void updateExpense(Expense expense);

    @Delete("DELETE FROM expenses WHERE expense_id = #{id}")
    void deleteExpense(Long id);

    @Select("SELECT * FROM expenses ORDER BY ${orderBy} ${direction} LIMIT #{limit} OFFSET #{offset}")
    List<Expense> getAllExpense(@Param("offset") int offset, @Param("limit") int limit, @Param("orderBy") String orderBy, @Param("direction") String direction);

}
