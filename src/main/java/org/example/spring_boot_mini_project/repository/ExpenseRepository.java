package org.example.spring_boot_mini_project.repository;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExpenseRepository {

    @Insert("INSERT INTO Expenses (amount, description, date, category_id) " +
            "VALUES (#{amount}, #{description}, #{expenseDate}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "expenseId")
    void createExpense(ExpenseRequest expense);


}
