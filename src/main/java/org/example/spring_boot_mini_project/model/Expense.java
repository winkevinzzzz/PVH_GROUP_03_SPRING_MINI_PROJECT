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
    private BigDecimal amount;
    private String description;
    private LocalDateTime date;
    private User user;
    private Category category;
}

