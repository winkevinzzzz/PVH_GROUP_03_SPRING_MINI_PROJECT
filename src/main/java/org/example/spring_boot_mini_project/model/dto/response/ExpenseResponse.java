package org.example.spring_boot_mini_project.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private UUID expenseId;
    private BigDecimal amount;
    private String description;
    private LocalDateTime expenseDate;
    private UserResponse user;
    private CategoryExpenseResponse category;
}
