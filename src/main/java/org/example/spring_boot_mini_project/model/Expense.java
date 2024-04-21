package org.example.spring_boot_mini_project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    private UUID expenseId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;
}
