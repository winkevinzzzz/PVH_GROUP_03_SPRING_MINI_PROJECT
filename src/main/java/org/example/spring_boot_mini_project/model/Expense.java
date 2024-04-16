package org.example.spring_boot_mini_project.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Expense {
    private UUID expense_id;
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private UUID user_id;

    @NotNull
    private UUID category_id;
}
