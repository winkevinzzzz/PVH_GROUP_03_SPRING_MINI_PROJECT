package org.example.spring_boot_mini_project.service.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_mini_project.model.Category;
import org.example.spring_boot_mini_project.model.Expense;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.ExpenseRequest;
import org.example.spring_boot_mini_project.model.dto.response.ExpenseCategoryResponse;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ExpenseResponse getExpenseById(UUID id) {
        return null;
    }

    @Override
    public ExpenseResponse getExpenseById(UUID id, UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        Expense expense = expenseRepository.getExpenseById(id, userId);
        if (expense == null) {
            return null; // Or handle the null case appropriately
        }
        ExpenseResponse expenseResponse = modelMapper.map(expense, ExpenseResponse.class);
        expenseResponse.setUser(userResponse);
        return expenseResponse;
    }

    @Override
    public Expense updateExpense(Long id, ExpenseRequest expenseRequest) {
        Expense existingExpense = expenseRepository.getExpenseById(id);
        if (existingExpense == null) {
            return null;
        }

        existingExpense.setAmount(expenseRequest.getAmount());
        existingExpense.setDescription(expenseRequest.getDescription());
        existingExpense.setDate(expenseRequest.getDate());
        existingExpense.setCategoryId(expenseRequest.getCategoryId());

        expenseRepository.updateExpense(existingExpense);

        return existingExpense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        expenseRepository.updateExpense(expense);
        return expense;
    }

    @Override
    public void deleteExpense(UUID id) {
        expenseRepository.deleteExpense(id);
    }

    @Override
    public List<Expense> getAllExpense(int offset, int limit, String sortBy, boolean ascending) {
        if (!"date".equals(sortBy) && !"category_id".equals(sortBy)) {
            throw new IllegalArgumentException("Invalid sort by field. Must be 'date' or 'category_id'.");
        }
        return expenseRepository.getAllExpense(offset, limit, sortBy, ascending ? "asc" : "desc");
    }

    @Override
    public List<Expense> getAllExpense() {
        return expenseRepository.getAllExpense(0, 10, "date", "asc");
    }

    @Override
    public Expense createExpense(ExpenseRequest expense) {
        return null;
    }

    @Override
    public Expense createExpense(ExpenseRequest expenseRequest) {
        return null;
    }

    @Override
    public ExpenseResponse addExpense(ExpenseRequest expenseRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        Category category = categoryRepository.getCategoryById(expenseRequest.getCategoryId(), user.getUserId());
        if (category == null) {
            return null; // Or handle the null case appropriately
        }
        ExpenseCategoryResponse categoryResponse = modelMapper.map(category, ExpenseCategoryResponse.class);
        Expense expense = new Expense();
        expense.setAmount(expenseRequest.getAmount());
        expense.setDescription(expenseRequest.getDescription());
        expense.setDate(LocalDateTime.now());


        Expense expenseRespond = expenseRepository.createExpense(expense);
        if (expenseRespond == null) {
            return null; // Or handle the null case appropriately
        }
        ExpenseResponse expenseResponse = modelMapper.map(expenseRespond, ExpenseResponse.class);
        expenseResponse.setUser(userResponse);
        expenseResponse.setExpense(categoryResponse);
        return expenseResponse;
    }
}
