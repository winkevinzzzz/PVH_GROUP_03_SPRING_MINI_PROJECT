package org.example.spring_boot_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.example.spring_boot_mini_project.model.Expense;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface ExpenseRepository {

    @Insert("INSERT INTO expenses (amount, description, date, category_id) " +
            "VALUES (#{amount}, #{description}, #{date}, #{category_id})")
    @Options(useGeneratedKeys = true, keyProperty = "expenseId")
    Expense createExpense(Expense expense);

    @Select("SELECT * FROM expenses WHERE expense_id = #{id}")
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
    void deleteExpense(UUID id);

    @Select("SELECT * FROM expenses ORDER BY ${sortBy} ${direction} LIMIT #{limit} OFFSET #{offset}")
    List<Expense> getAllExpense(@Param("offset") int offset, @Param("limit") int limit, @Param("sortBy") String sortBy, @Param("direction") String direction);

    Expense getExpenseById(UUID id, UUID userId);
}
