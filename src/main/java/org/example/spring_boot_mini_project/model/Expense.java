package org.example.spring_boot_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private UUID expenseId;
    private int amount;
    private String description;
    private Date date;
    private UUID userId;
    private UUID categoryId;
}
