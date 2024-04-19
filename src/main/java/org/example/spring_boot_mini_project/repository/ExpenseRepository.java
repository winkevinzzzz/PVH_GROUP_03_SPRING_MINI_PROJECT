package org.example.spring_boot_mini_project.repository;


import org.apache.ibatis.annotations.*;
import org.example.spring_boot_mini_project.config.typeHandler;
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
        WHERE user_id =#{userID}::uuid
    """)
    @Results(id = "expenseMapping", value = {
            @Result(property = "expenseId",column = "expense_id",typeHandler = typeHandler.class),
            @Result(property = "amount",column = "amount"),
            @Result(property = "description",column = "description"),
            @Result(property = "date",column = "date"),
            @Result(property = "user",column = "user_id",
            one = @One(select = "org.example.spring_boot_mini_project.repository.UserRepository.findById")),
            @Result(property = "category",column = "category_id",one = @One(select = "org.example.spring_boot_mini_project.repository.CategoryRepository.findCategoryById"))
    })
    List<Expense> getAllExpense(UUID userID);

    @Select("""
    INSERT INTO expenses(amount, description, date, user_id, category_id) VALUES (#{expense.amount},#{expense.description},#{expense.date},#{expense.user.userId}::uuid,#{expense.category.categoryID}::uuid) RETURNING *
    """)
    @ResultMap("expenseMapping")
    Expense addExpense(@Param("expense") Expense expense);

    @Select("""
    SELECT * FROM expenses
    WHERE expense_id =#{id}::UUID AND user_id =#{userId}::UUID
    """)
    @ResultMap("expenseMapping")
    Expense getExpenseById(UUID id , UUID userId);

    @Delete("""
    DELETE FROM expenses
    WHERE expense_id =#{id}::UUID AND user_id =#{userId}::UUID
    """)
    void deleteExpenseById(UUID id, UUID userId);

    @Select("""
    UPDATE expenses SET amount = #{expense.amount}, description = #{expense.description}, date = #{expense.date}, category_id = #{expense.categoryId}::UUID
    WHERE expense_id = #{id}::uuid RETURNING *
    """)
    @ResultMap("expenseMapping")
    Expense updateExpenseById(UUID id, UUID userId, @Param("expense") ExpenseRequest expenseRequest);
}
