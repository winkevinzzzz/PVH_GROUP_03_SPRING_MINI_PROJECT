package org.example.spring_boot_mini_project.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    private UUID expenseId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Date is required")
    private LocalDateTime expense_date;

    @NotNull(message = "User ID is required")
    private UUID user_id;

    @NotNull(message = "Category ID is required")
    private UUID category_id;
}

