package org.example.spring_boot_mini_project.service.ServiceImp;


import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.CategoryExpenseResponse;
import org.example.spring_boot_mini_project.model.dto.response.CategoryResponse;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.repository.CategoryRepository;
import org.example.spring_boot_mini_project.repository.ExpenseRepository;
import org.example.spring_boot_mini_project.repository.UserRepository;
import org.example.spring_boot_mini_project.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ExpenseResponse getExpenseById(UUID id, UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        System.out.println(user.toString());
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        Expense expense =expenseRepository.getExpenseById(id,userId);
        System.out.println(expense.toString());
        ExpenseResponse expenseResponse =modelMapper.map(expense,ExpenseResponse.class);
        expenseResponse.setUser(userResponse);
        return expenseResponse;
    }

    @Override
    public Expense updateExpense(UUID id, Expense expense) {
        return null;
    }


    @Override
    public List<ExpenseResponse> getAllExpenses(UUID userId) {
        List<Expense> expenses = expenseRepository.getAllExpense(userId);
        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        for(Expense expense : expenses){
            ExpenseResponse expenseResponse = modelMapper.map(expense,ExpenseResponse.class);
            expenseResponses.add(expenseResponse);
        }
        return expenseResponses ;
    }

    @Override
    public ExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        Category category = categoryRepository.getCategoryById(expenseRequest.getCategoryId(),user.getUserId());
        CategoryExpenseResponse categoryResponse = modelMapper.map(category, CategoryExpenseResponse.class);
        Expense expense = new Expense();
        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setDate(LocalDateTime.now());
        expense.setUser(user);
        expense.setCategory(category);

        Expense expenseRespond = expenseRepository.addExpense(expense);
        ExpenseResponse expenseResponse = modelMapper.map(expenseRespond,ExpenseResponse.class);
        expenseResponse.setUser(userResponse);
        expenseResponse.setCategory(categoryResponse);
        return expenseResponse;
    }

    @Override
    public void deleteExpenseById(UUID id, UUID userId) {
        expenseRepository.deleteExpenseById(id,userId);
    }

    @Override
    public ExpenseResponse updateExpenseById(UUID id, UUID userId, ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.updateExpenseById(id,userId,expenseRequest);
        return modelMapper.map(expense, ExpenseResponse.class);
    }


}
